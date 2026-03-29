package com.phase.bookpin.bookmark.add

import androidx.lifecycle.viewModelScope
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.domain.book.BookRepository
import com.phase.bookpin.domain.image.ImageRepository
import com.phase.bookpin.model.bookmark.BookmarkType
import kotlinx.coroutines.launch

class AddBookmarkViewModel(
    private val textRecognizer: TextRecognizer,
    private val bookRepository: BookRepository,
    private val imageRepository: ImageRepository,
    private val imageFileReader: ImageFileReader,
) : BaseViewModel<AddBookmarkState, AddBookmarkSideEffect>() {
    override fun createInitialState(): AddBookmarkState = AddBookmarkState()

    private var bookId: Long = 0L

    fun init(bookId: Long, bookmarkType: BookmarkType) {
        this.bookId = bookId
        reduce { copy(bookmarkType = bookmarkType) }
    }

    fun onExtractedTextChanged(text: String) {
        reduce { copy(extractedText = text) }
    }

    fun onPageNumberChanged(page: String) {
        reduce { copy(pageNumber = page) }
    }

    fun onPersonalMemoChanged(memo: String) {
        reduce { copy(personalMemo = memo) }
    }

    fun onSaveBookmark() {
        val state = uiState.value
        if (state.isLoading) return

        viewModelScope.launch {
            reduce { copy(isLoading = true) }

            val imageUrlResult = if (state.bookmarkType != BookmarkType.TEXT && state.photoUri != null) {
                runCatching {
                    val extension = imageFileReader.getExtension(state.photoUri)
                    val bytes = imageFileReader.readBytes(state.photoUri)
                    imageRepository.uploadImage(bytes, extension).getOrThrow()
                }
            } else {
                Result.success("")
            }

            imageUrlResult.onFailure { error ->
                reduce { copy(isLoading = false) }
                postSideEffect(
                    AddBookmarkSideEffect.ShowSnackbar(
                        error.message ?: "이미지 업로드에 실패했습니다.",
                    ),
                )
                return@launch
            }

            val imageUrl = imageUrlResult.getOrThrow()
            val pageNumber = state.pageNumber.toIntOrNull() ?: 0

            bookRepository
                .createBookmark(
                    bookId = bookId,
                    pageNumber = pageNumber,
                    extractedText = state.extractedText,
                    note = state.personalMemo,
                    imageUrl = imageUrl,
                ).onSuccess {
                    reduce { copy(isLoading = false) }
                    postSideEffect(AddBookmarkSideEffect.BookmarkSaved)
                }.onFailure { error ->
                    reduce { copy(isLoading = false) }
                    postSideEffect(
                        AddBookmarkSideEffect.ShowSnackbar(
                            error.message ?: "책갈피 저장에 실패했습니다.",
                        ),
                    )
                }
        }
    }

    fun onPhotoUriChanged(uri: String?) {
        reduce { copy(photoUri = uri) }
    }

    fun onCroppedImageReady(uri: String?) {
        if (uri == null) {
            reduce { copy(photoUri = null) }
            return
        }
        reduce { copy(photoUri = uri, isOcrProcessing = true) }
        viewModelScope.launch {
            val result = textRecognizer.recognizeText(uri)
            result.onSuccess { text ->
                reduce { copy(extractedText = text, isOcrProcessing = false) }
            }
            result.onFailure { error ->
                reduce { copy(isOcrProcessing = false) }
                postSideEffect(
                    AddBookmarkSideEffect.ShowSnackbar(
                        error.message ?: "텍스트 인식에 실패했습니다.",
                    ),
                )
            }
        }
    }

    fun onCloseClick() {
        postSideEffect(AddBookmarkSideEffect.NavigateBack)
    }

    override fun onCleared() {
        super.onCleared()
        textRecognizer.close()
    }
}
