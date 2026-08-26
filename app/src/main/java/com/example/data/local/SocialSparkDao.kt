package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SocialSparkDao {

    // Avatar
    @Query("SELECT * FROM avatar_config WHERE id = 1")
    fun getAvatar(): Flow<AvatarEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAvatar(avatar: AvatarEntity)

    // Progress
    @Query("SELECT * FROM scenario_progress")
    fun getAllProgress(): Flow<List<ScenarioProgressEntity>>

    @Query("SELECT * FROM scenario_progress WHERE scenarioId = :scenarioId")
    suspend fun getProgressForScenario(scenarioId: String): ScenarioProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveScenarioProgress(progress: ScenarioProgressEntity)

    // Activity Logs
    @Query("SELECT * FROM activity_logs ORDER BY timestamp DESC")
    fun getAllActivityLogs(): Flow<List<ActivityLogEntity>>

    @Query("SELECT * FROM activity_logs WHERE dateString = :dateString ORDER BY timestamp DESC")
    fun getActivityLogsForDate(dateString: String): Flow<List<ActivityLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivityLog(log: ActivityLogEntity)

    // Stickers
    @Query("SELECT * FROM unlocked_stickers")
    fun getUnlockedStickers(): Flow<List<UnlockedStickerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun unlockSticker(sticker: UnlockedStickerEntity)

    // User Progress / Stars
    @Query("SELECT * FROM user_progress_stats WHERE id = 1")
    fun getUserProgress(): Flow<UserProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProgress(progress: UserProgressEntity)

    // Parental Settings
    @Query("SELECT * FROM parental_settings WHERE id = 1")
    fun getParentalSettings(): Flow<ParentalSettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveParentalSettings(settings: ParentalSettingsEntity)
}
