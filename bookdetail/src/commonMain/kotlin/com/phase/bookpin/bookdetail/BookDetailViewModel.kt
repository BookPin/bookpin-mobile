package com.phase.bookpin.bookdetail

import com.phase.bookpin.common.BaseViewModel

class BookDetailViewModel : BaseViewModel<BookDetailState, BookDetailSideEffect>() {
    override fun createInitialState(): BookDetailState = BookDetailState(
        book = BookDetail(
            id = "1",
            title = "미드나잇 라이브러리",
            author = "매트 헤이그",
            coverImageUrl = "",
            currentPage = 208,
            totalPages = 320,
            bookmarkCount = 12,
        ),
        bookmarks = listOf(
            Bookmark(
                id = "1",
                pageNumber = 42,
                quote = "\"후회는 우리가 다르게 살 수 있었다는 희망을 품게 해준다.\"",
                memo = "이 문장이 정말 와닿았다. 과거에 얽매이지 않고 앞으로 나아가야 한다.",
                type = BookmarkTab.TEXT,
            ),
            Bookmark(
                id = "2",
                pageNumber = 78,
                quote = "\"모든 삶에는 그 자체의 아름다움이 있다.\"",
                memo = "",
                type = BookmarkTab.TEXT,
            ),
            Bookmark(
                id = "3",
                pageNumber = 156,
                quote = "\"가장 좋은 체스 수는 다음 수다.\"",
                memo = "인생도 마찬가지. 지금 할 수 있는 최선에 집중하자.",
                type = BookmarkTab.TEXT,
            ),
        ),
        selectedTab = BookmarkTab.TEXT,
    )

    fun onBackClick() {
        postSideEffect(BookDetailSideEffect.NavigateBack)
    }

    fun onTabSelected(tab: BookmarkTab) {
        reduce { copy(selectedTab = tab) }
    }

    fun onMarkAsCompleteClick() {
        reduce { copy(book = book.copy(isCompleted = true)) }
        postSideEffect(BookDetailSideEffect.ShowSnackbar("완독으로 표시되었습니다"))
    }

    fun onAddBookmarkClick() {
        postSideEffect(BookDetailSideEffect.NavigateToAddBookmark)
    }

    val textBookmarkCount: Int
        get() = uiState.value.bookmarks.count { it.type == BookmarkTab.TEXT }

    val photoBookmarkCount: Int
        get() = uiState.value.bookmarks.count { it.type == BookmarkTab.PHOTO }
}
