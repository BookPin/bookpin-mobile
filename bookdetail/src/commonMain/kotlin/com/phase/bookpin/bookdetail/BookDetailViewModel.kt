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
            Bookmark(
                id = "4",
                pageNumber = 23,
                quote = "\"삶은 가능성으로 가득 차 있다.\"",
                memo = "첫 번째 사진 북마크 테스트",
                type = BookmarkTab.PHOTO,
                photoUrl = "https://picsum.photos/seed/book1/400/400",
            ),
            Bookmark(
                id = "5",
                pageNumber = 67,
                quote = "\"우리는 모두 연결되어 있다.\"",
                memo = "",
                type = BookmarkTab.PHOTO,
                photoUrl = "https://picsum.photos/seed/book2/400/400",
            ),
            Bookmark(
                id = "6",
                pageNumber = 112,
                quote = "\"변화는 두려운 것이 아니라 새로운 시작이다.\"",
                memo = "이미지 없는 경우 placeholder 확인용",
                type = BookmarkTab.PHOTO,
                photoUrl = null,
            ),
            Bookmark(
                id = "7",
                pageNumber = 198,
                quote = "\"시간은 우리가 가진 가장 소중한 자원이다. 그것을 어떻게 쓰느냐가 곧 우리의 삶을 결정한다.\"",
                memo = "긴 텍스트 말줄임 테스트용",
                type = BookmarkTab.PHOTO,
                photoUrl = "https://picsum.photos/seed/book3/400/400",
            ),
            Bookmark(
                id = "8",
                pageNumber = 245,
                quote = "\"꿈은 포기하지 않는 한 사라지지 않는다.\"",
                memo = "",
                type = BookmarkTab.PHOTO,
                photoUrl = "https://picsum.photos/seed/book4/400/400",
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
