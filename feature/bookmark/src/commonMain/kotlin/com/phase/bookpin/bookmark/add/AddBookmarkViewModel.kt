package com.phase.bookpin.bookmark.add

import androidx.lifecycle.viewModelScope
import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.model.bookmark.BookmarkType
import kotlinx.coroutines.launch

class AddBookmarkViewModel(
    private val textRecognizer: TextRecognizer,
) : BaseViewModel<AddBookmarkState, AddBookmarkSideEffect>() {
    override fun createInitialState(): AddBookmarkState = AddBookmarkState()

    fun initBookmarkType(type: BookmarkType) {
        reduce { copy(bookmarkType = type) }
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
        // TODO: API 연동 후 구현
        postSideEffect(AddBookmarkSideEffect.BookmarkSaved)
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
