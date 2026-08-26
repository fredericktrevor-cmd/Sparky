package com.example.data.model

enum class DifficultyLevel(val displayName: String, val choiceCount: Int, val description: String) {
    GENTLE_STEP("Gentle Step (2 Choices)", 2, "Simplified choices with large visual cues and high hints. Best for early learners."),
    GROWING_CONFIDENCE("Growing Confidence (3 Choices)", 3, "Standard real-world social scenarios with 3 distinct behavioral choices."),
    SUPER_CHALLENGE("Super Explorer (3 Choices + Nuanced)", 3, "More diverse situational social cues and reflection steps.")
}

data class ParentalSettings(
    val difficulty: DifficultyLevel = DifficultyLevel.GENTLE_STEP,
    val visualHintsAlwaysVisible: Boolean = true,
    val autoPlayNarration: Boolean = true,
    val sensoryComfortMode: Boolean = false, // Soft pastel colors, reduced confetti/particle speed, gentle audio
    val narrationSpeed: Float = 0.9f, // 0.75f to 1.1f
    val narrationPitch: Float = 1.15f, // Friendly companion pitch
    val dailyGoalMinutes: Int = 15,
    val soundEffectsEnabled: Boolean = true,
    val parentPin: String = "1234",
    val textScaleMultiplier: Float = 1.0f
)
