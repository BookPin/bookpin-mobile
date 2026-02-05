package com.phase.bookpin.home

import com.phase.bookpin.common.BaseViewModel

class HomeViewModel : BaseViewModel<HomeState, HomeSideEffect>() {
    override fun createInitialState(): HomeState = HomeState(
        dailyQuote = DailyQuote(
            text = "\"독서는 마음에 대한 휴식이다.\"",
            author = "— 공자",
        ),
        books = listOf(
            Book(
                id = "1",
                title = "미드나잇 라이브러리",
                author = "매트 헤이그",
                coverImageUrl = "",
                currentPage = 208,
                totalPages = 320,
                bookmarkCount = 12,
            ),
            Book(
                id = "2",
                title = "아주 작은 습관의 힘",
                author = "제임스 클리어",
                coverImageUrl = "",
                currentPage = 112,
                totalPages = 280,
                bookmarkCount = 8,
            ),
        ),
    )

    fun onSettingsClick() {
        postSideEffect(HomeSideEffect.NavigateToSettings)
    }

    fun onAddBookClick() {
        postSideEffect(HomeSideEffect.NavigateToAddBook)
    }

    fun onBookClick(bookId: String) {
        postSideEffect(HomeSideEffect.NavigateToBookDetail(bookId))
    }
}
