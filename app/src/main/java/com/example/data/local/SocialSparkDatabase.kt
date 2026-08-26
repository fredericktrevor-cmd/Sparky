package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        AvatarEntity::class,
        ScenarioProgressEntity::class,
        ActivityLogEntity::class,
        UnlockedStickerEntity::class,
        UserProgressEntity::class,
        ParentalSettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SocialSparkDatabase : RoomDatabase() {
    abstract fun socialSparkDao(): SocialSparkDao

    companion object {
        @Volatile
        private var INSTANCE: SocialSparkDatabase? = null

        fun getDatabase(context: Context): SocialSparkDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SocialSparkDatabase::class.java,
                    "social_spark_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
