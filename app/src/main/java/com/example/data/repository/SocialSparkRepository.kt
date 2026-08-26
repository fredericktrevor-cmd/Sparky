package com.example.data.repository

import com.example.data.local.ActivityLogEntity
import com.example.data.local.AvatarEntity
import com.example.data.local.ParentalSettingsEntity
import com.example.data.local.ScenarioProgressEntity
import com.example.data.local.SocialSparkDao
import com.example.data.local.UnlockedStickerEntity
import com.example.data.local.UserProgressEntity
import com.example.data.model.AvatarBuddy
import com.example.data.model.AvatarConfig
import com.example.data.model.AvatarExpression
import com.example.data.model.AvatarOutfit
import com.example.data.model.AuraSparkle
import com.example.data.model.CategoryMasteryStat
import com.example.data.model.DailyActivityLog
import com.example.data.model.DifficultyLevel
import com.example.data.model.ParentalSettings
import com.example.data.model.SensoryAccessory
import com.example.data.model.SkillCategory
import com.example.data.model.SocialScenario
import com.example.data.model.StickerItem
import com.example.data.model.TrophyMilestone
import com.example.data.model.UserProgress
import com.example.data.scenarios.PreloadedScenarios
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SocialSparkRepository(
    private val dao: SocialSparkDao
) {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun getAllScenarios(): List<SocialScenario> = PreloadedScenarios.allScenarios

    fun getScenarioById(id: String): SocialScenario? {
        return PreloadedScenarios.allScenarios.find { it.id == id }
    }

    // Avatar config flow
    val avatarConfig: Flow<AvatarConfig> = dao.getAvatar().map { entity ->
        if (entity == null) {
            AvatarConfig()
        } else {
            AvatarConfig(
                kidName = entity.kidName,
                buddy = AvatarBuddy.entries.find { it.id == entity.buddyId } ?: AvatarBuddy.SPARK_ROBOT,
                expression = AvatarExpression.entries.find { it.id == entity.expressionId } ?: AvatarExpression.HAPPY,
                outfit = AvatarOutfit.entries.find { it.id == entity.outfitId } ?: AvatarOutfit.HERO_CAPE,
                accessory = SensoryAccessory.entries.find { it.id == entity.accessoryId } ?: SensoryAccessory.NOISE_HEADPHONES,
                aura = AuraSparkle.entries.find { it.id == entity.auraId } ?: AuraSparkle.SUNSHINE_GOLD
            )
        }
    }

    suspend fun saveAvatarConfig(config: AvatarConfig) {
        dao.saveAvatar(
            AvatarEntity(
                id = 1,
                kidName = config.kidName,
                buddyId = config.buddy.id,
                expressionId = config.expression.id,
                outfitId = config.outfit.id,
                accessoryId = config.accessory.id,
                auraId = config.aura.id
            )
        )
    }

    // Parental Settings Flow
    val parentalSettings: Flow<ParentalSettings> = dao.getParentalSettings().map { entity ->
        if (entity == null) {
            ParentalSettings()
        } else {
            ParentalSettings(
                difficulty = DifficultyLevel.entries.find { it.name == entity.difficultyName } ?: DifficultyLevel.GENTLE_STEP,
                visualHintsAlwaysVisible = entity.visualHintsAlwaysVisible,
                autoPlayNarration = entity.autoPlayNarration,
                sensoryComfortMode = entity.sensoryComfortMode,
                narrationSpeed = entity.narrationSpeed,
                narrationPitch = entity.narrationPitch,
                dailyGoalMinutes = entity.dailyGoalMinutes,
                soundEffectsEnabled = entity.soundEffectsEnabled,
                parentPin = entity.parentPin,
                textScaleMultiplier = entity.textScaleMultiplier
            )
        }
    }

    suspend fun saveParentalSettings(settings: ParentalSettings) {
        dao.saveParentalSettings(
            ParentalSettingsEntity(
                id = 1,
                difficultyName = settings.difficulty.name,
                visualHintsAlwaysVisible = settings.visualHintsAlwaysVisible,
                autoPlayNarration = settings.autoPlayNarration,
                sensoryComfortMode = settings.sensoryComfortMode,
                narrationSpeed = settings.narrationSpeed,
                narrationPitch = settings.narrationPitch,
                dailyGoalMinutes = settings.dailyGoalMinutes,
                soundEffectsEnabled = settings.soundEffectsEnabled,
                parentPin = settings.parentPin,
                textScaleMultiplier = settings.textScaleMultiplier
            )
        )
    }

    // User Progress Flow
    val userProgress: Flow<UserProgress> = dao.getUserProgress().map { entity ->
        if (entity == null) {
            UserProgress(totalStars = 60, currentStreakDays = 2, completedScenariosCount = 1, unlockedStickersCount = 2)
        } else {
            val level = (entity.totalStars / 100) + 1
            val levelTitle = when (level) {
                1 -> "Junior Explorer"
                2 -> "Playground Buddy"
                3 -> "Social Spark Star"
                4 -> "Calm Champion"
                else -> "Master Friendship Hero"
            }
            UserProgress(
                totalStars = entity.totalStars,
                currentStreakDays = entity.currentStreakDays,
                completedScenariosCount = 0, // calculated from scenario progress
                levelNumber = level,
                levelTitle = levelTitle
            )
        }
    }

    // Stickers Flow
    val stickers: Flow<List<StickerItem>> = dao.getUnlockedStickers().map { unlockedEntities ->
        val unlockedIds = unlockedEntities.map { it.stickerId }.toSet()
        PreloadedScenarios.allStickers.map { sticker ->
            sticker.copy(
                isUnlocked = sticker.isUnlocked || unlockedIds.contains(sticker.id)
            )
        }
    }

    // Trophies Flow
    fun getTrophies(totalStars: Int): List<TrophyMilestone> {
        return PreloadedScenarios.allTrophies.map { trophy ->
            trophy.copy(isUnlocked = totalStars >= trophy.starsRequired)
        }
    }

    // Scenario Progress
    val scenarioProgress: Flow<Map<String, ScenarioProgressEntity>> = dao.getAllProgress().map { list ->
        list.associateBy { it.scenarioId }
    }

    // Activity Logs
    val activityLogs: Flow<List<DailyActivityLog>> = dao.getAllActivityLogs().map { list ->
        list.map { entity ->
            DailyActivityLog(
                id = entity.id,
                dateString = entity.dateString,
                scenarioId = entity.scenarioId,
                scenarioTitle = entity.scenarioTitle,
                category = SkillCategory.entries.find { it.id == entity.categoryId } ?: SkillCategory.PLAYGROUND,
                isCompleted = entity.isCompleted,
                starsEarned = entity.starsEarned,
                attemptsCount = entity.attemptsCount,
                firstAttemptSuccess = entity.firstAttemptSuccess,
                durationSeconds = entity.durationSeconds,
                timestamp = entity.timestamp
            )
        }
    }

    suspend fun recordScenarioCompletion(
        scenario: SocialScenario,
        starsEarned: Int,
        attemptsCount: Int,
        firstAttemptSuccess: Boolean,
        durationSeconds: Int
    ) {
        val today = dateFormat.format(Date())
        val existingProgress = dao.getProgressForScenario(scenario.id)
        val newCompletedCount = (existingProgress?.completedCount ?: 0) + 1
        val highestStars = maxOf(existingProgress?.highestStars ?: 0, starsEarned)

        // Save progress
        dao.saveScenarioProgress(
            ScenarioProgressEntity(
                scenarioId = scenario.id,
                completedCount = newCompletedCount,
                highestStars = highestStars,
                lastCompletedTimestamp = System.currentTimeMillis(),
                isFavorite = existingProgress?.isFavorite ?: false
            )
        )

        // Log activity
        dao.insertActivityLog(
            ActivityLogEntity(
                dateString = today,
                scenarioId = scenario.id,
                scenarioTitle = scenario.title,
                categoryId = scenario.category.id,
                isCompleted = true,
                starsEarned = starsEarned,
                attemptsCount = attemptsCount,
                firstAttemptSuccess = firstAttemptSuccess,
                durationSeconds = durationSeconds,
                timestamp = System.currentTimeMillis()
            )
        )

        // Unlock sticker if specified
        if (scenario.unlockStickerId.isNotEmpty()) {
            dao.unlockSticker(
                UnlockedStickerEntity(
                    stickerId = scenario.unlockStickerId,
                    unlockedAtTimestamp = System.currentTimeMillis()
                )
            )
        }
    }

    suspend fun addStars(stars: Int) {
        // Will be updated via ViewModel combining with current progress
    }

    suspend fun saveUserProgress(progress: UserProgress) {
        val today = dateFormat.format(Date())
        dao.saveUserProgress(
            UserProgressEntity(
                id = 1,
                totalStars = progress.totalStars,
                currentStreakDays = progress.currentStreakDays,
                lastPlayedDateString = today
            )
        )
    }

    fun computeCategoryMastery(
        progressMap: Map<String, ScenarioProgressEntity>,
        logs: List<DailyActivityLog>
    ): List<CategoryMasteryStat> {
        return SkillCategory.entries.map { category ->
            val categoryScenarios = PreloadedScenarios.allScenarios.filter { it.category == category }
            val completedScenarios = categoryScenarios.count { progressMap.containsKey(it.id) }
            val categoryLogs = logs.filter { it.category == category }
            val accuracy = if (categoryLogs.isNotEmpty()) {
                val successfulAttempts = categoryLogs.count { it.firstAttemptSuccess }
                ((successfulAttempts.toFloat() / categoryLogs.size) * 100).toInt()
            } else {
                if (completedScenarios > 0) 100 else 0
            }
            CategoryMasteryStat(
                category = category,
                completedCount = completedScenarios,
                totalScenarios = categoryScenarios.size,
                accuracyPercentage = accuracy
            )
        }
    }
}
