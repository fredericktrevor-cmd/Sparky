package com.example.data.model

data class DailyActivityLog(
    val id: Long = 0,
    val dateString: String,
    val scenarioId: String,
    val scenarioTitle: String,
    val category: SkillCategory,
    val isCompleted: Boolean,
    val starsEarned: Int,
    val attemptsCount: Int,
    val firstAttemptSuccess: Boolean,
    val durationSeconds: Int,
    val timestamp: Long = System.currentTimeMillis()
)

data class CategoryMasteryStat(
    val category: SkillCategory,
    val completedCount: Int,
    val totalScenarios: Int,
    val accuracyPercentage: Int
)

data class EvidenceBasedParentTip(
    val id: String,
    val title: String,
    val sourceOrganization: String,
    val category: SkillCategory,
    val summary: String,
    val practicalHomeAction: String,
    val iconEmoji: String
)
