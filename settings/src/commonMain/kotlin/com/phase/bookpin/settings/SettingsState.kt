package com.phase.bookpin.settings

data class SettingsState(
    val profileName: String = "독서하는 사람",
    val accountType: String = "카카오 계정",
    val achievements: List<Achievement> = listOf(
        Achievement(
            emoji = "\uD83D\uDCD6",
            title = "첫 책갈피",
            description = "첫 책갈피를 저장했어요! 계속 기록해보세요.",
            date = "2026년 1월 18일",
        ),
    ),
)

data class Achievement(
    val emoji: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
)
