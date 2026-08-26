package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avatar_config")
data class AvatarEntity(
    @PrimaryKey val id: Int = 1,
    val kidName: String,
    val buddyId: String,
    val expressionId: String,
    val outfitId: String,
    val accessoryId: String,
    val auraId: String
)

@Entity(tableName = "scenario_progress")
data class ScenarioProgressEntity(
    @PrimaryKey val scenarioId: String,
    val completedCount: Int,
    val highestStars: Int,
    val lastCompletedTimestamp: Long,
    val isFavorite: Boolean = false
)

@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateString: String,
    val scenarioId: String,
    val scenarioTitle: String,
    val categoryId: String,
    val isCompleted: Boolean,
    val starsEarned: Int,
    val attemptsCount: Int,
    val firstAttemptSuccess: Boolean,
    val durationSeconds: Int,
    val timestamp: Long
)

@Entity(tableName = "unlocked_stickers")
data class UnlockedStickerEntity(
    @PrimaryKey val stickerId: String,
    val unlockedAtTimestamp: Long
)

@Entity(tableName = "user_progress_stats")
data class UserProgressEntity(
    @PrimaryKey val id: Int = 1,
    val totalStars: Int,
    val currentStreakDays: Int,
    val lastPlayedDateString: String
)

@Entity(tableName = "parental_settings")
data class ParentalSettingsEntity(
    @PrimaryKey val id: Int = 1,
    val difficultyName: String,
    val visualHintsAlwaysVisible: Boolean,
    val autoPlayNarration: Boolean,
    val sensoryComfortMode: Boolean,
    val narrationSpeed: Float,
    val narrationPitch: Float,
    val dailyGoalMinutes: Int,
    val soundEffectsEnabled: Boolean,
    val parentPin: String,
    val textScaleMultiplier: Float
)
