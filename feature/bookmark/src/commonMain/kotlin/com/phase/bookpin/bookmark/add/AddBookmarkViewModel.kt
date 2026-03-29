package com.phase.bookpin.bookmark.add

import com.phase.bookpin.common.BaseViewModel
import com.phase.bookpin.model.bookmark.BookmarkType

class AddBookmarkViewModel : BaseViewModel<AddBookmarkState, AddBookmarkSideEffect>() {
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

    fun onCloseClick() {
        postSideEffect(AddBookmarkSideEffect.NavigateBack)
    }
}
