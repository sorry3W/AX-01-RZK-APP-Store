package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.AppDao
import com.example.data.local.entity.AppReviewEntity
import com.example.data.local.entity.CustomAppEntity
import com.example.data.local.entity.InstalledAppEntity
import com.example.data.local.entity.SearchHistoryEntity
import com.example.data.local.entity.SubscriptionEntity
import com.example.data.local.entity.WishlistEntity

@Database(
    entities = [
        InstalledAppEntity::class,
        WishlistEntity::class,
        AppReviewEntity::class,
        CustomAppEntity::class,
        SubscriptionEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ax01_app_store.db"
                ).fallbackToDestructiveMigration(false)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
