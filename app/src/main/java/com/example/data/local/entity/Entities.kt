package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "installed_apps")
data class InstalledAppEntity(
    @PrimaryKey val id: String,
    val installedAt: Long = System.currentTimeMillis(),
    val sizeMb: Double,
    val version: String,
    val isCustomCreated: Boolean = false
)

@Entity(tableName = "wishlist")
data class WishlistEntity(
    @PrimaryKey val appId: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "app_reviews")
data class AppReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val appId: String,
    val authorName: String,
    val rating: Int,
    val comment: String,
    val timestamp: Long = System.currentTimeMillis(),
    val helpfulLikes: Int = 0
)

@Entity(tableName = "custom_apps")
data class CustomAppEntity(
    @PrimaryKey val id: String,
    val title: String,
    val tagline: String,
    val description: String,
    val category: String,
    val packageId: String,
    val version: String,
    val accentColorHex: Long,
    val iconVectorName: String,
    val sandboxTemplate: String,
    val sizeMb: Double,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "subscription")
data class SubscriptionEntity(
    @PrimaryKey val id: Int = 1,
    val tierId: String,
    val planName: String,
    val paymentMethod: String,
    val transactionId: String,
    val activatedAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = System.currentTimeMillis() + (60L * 24 * 60 * 60 * 1000)
)

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey val query: String,
    val searchedAt: Long = System.currentTimeMillis()
)
