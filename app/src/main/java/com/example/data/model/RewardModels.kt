package com.example.data.model

data class StickerItem(
    val id: String,
    val name: String,
    val emoji: String,
    val colorHex: Long,
    val category: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val unlockScenarioId: String? = null
)

data class TrophyMilestone(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val starsRequired: Int,
    val isUnlocked: Boolean = false
)

data class UserProgress(
    val totalStars: Int = 50,
    val currentStreakDays: Int = 3,
    val completedScenariosCount: Int = 0,
    val unlockedStickersCount: Int = 2,
    val levelNumber: Int = 1,
    val levelTitle: String = "Junior Explorer"
) {
    val nextLevelStars: Int
        get() = (levelNumber * 100)

    val progressToNextLevel: Float
        get() {
            val prevTier = (levelNumber - 1) * 100
            val currentInTier = (totalStars - prevTier).coerceAtLeast(0)
            return (currentInTier.toFloat() / 100f).coerceIn(0f, 1f)
        }
}
