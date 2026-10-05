package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.AppReviewEntity
import com.example.data.local.entity.CustomAppEntity
import com.example.data.local.entity.InstalledAppEntity
import com.example.data.local.entity.SearchHistoryEntity
import com.example.data.local.entity.SubscriptionEntity
import com.example.data.local.entity.WishlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Installed Apps
    @Query("SELECT * FROM installed_apps ORDER BY installedAt DESC")
    fun getInstalledApps(): Flow<List<InstalledAppEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun installApp(app: InstalledAppEntity)

    @Query("DELETE FROM installed_apps WHERE id = :appId")
    suspend fun uninstallApp(appId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM installed_apps WHERE id = :appId)")
    fun isAppInstalled(appId: String): Flow<Boolean>

    // Wishlist
    @Query("SELECT * FROM wishlist ORDER BY savedAt DESC")
    fun getWishlist(): Flow<List<WishlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWishlist(item: WishlistEntity)

    @Query("DELETE FROM wishlist WHERE appId = :appId")
    suspend fun removeFromWishlist(appId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist WHERE appId = :appId)")
    fun isWishlisted(appId: String): Flow<Boolean>

    // Reviews
    @Query("SELECT * FROM app_reviews WHERE appId = :appId ORDER BY timestamp DESC")
    fun getReviewsForApp(appId: String): Flow<List<AppReviewEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: AppReviewEntity)

    @Query("UPDATE app_reviews SET helpfulLikes = helpfulLikes + 1 WHERE id = :reviewId")
    suspend fun incrementHelpful(reviewId: Long)

    // Custom Created Apps
    @Query("SELECT * FROM custom_apps ORDER BY createdAt DESC")
    fun getCustomApps(): Flow<List<CustomAppEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomApp(app: CustomAppEntity)

    @Query("DELETE FROM custom_apps WHERE id = :id")
    suspend fun deleteCustomApp(id: String)

    // Subscription
    @Query("SELECT * FROM subscription WHERE id = 1 LIMIT 1")
    fun getSubscription(): Flow<SubscriptionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSubscription(subscription: SubscriptionEntity)

    // Search History
    @Query("SELECT * FROM search_history ORDER BY searchedAt DESC LIMIT 20")
    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchQuery(item: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE `query` = :query")
    suspend fun deleteSearchQuery(query: String)

    @Query("DELETE FROM search_history")
    suspend fun clearSearchHistory()
}
