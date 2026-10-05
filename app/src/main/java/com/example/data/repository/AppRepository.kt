package com.example.data.repository

import com.example.data.SampleAppData
import com.example.data.local.dao.AppDao
import com.example.data.local.entity.AppReviewEntity
import com.example.data.local.entity.CustomAppEntity
import com.example.data.local.entity.InstalledAppEntity
import com.example.data.local.entity.SearchHistoryEntity
import com.example.data.local.entity.SubscriptionEntity
import com.example.data.local.entity.WishlistEntity
import com.example.model.AppItem
import com.example.model.Category
import com.example.model.SandboxType
import com.example.model.SubscriptionTier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class AppRepository(private val appDao: AppDao) {

    // Combined catalog: Flagship apps + Custom created apps from Room
    val allAppsFlow: Flow<List<AppItem>> = appDao.getCustomApps().map { customEntities ->
        val customApps = customEntities.map { entity ->
            AppItem(
                id = entity.id,
                title = entity.title,
                tagline = entity.tagline,
                description = entity.description,
                developer = "User Creator [AX-01 Studio]",
                category = Category.fromString(entity.category),
                rating = 5.0f,
                reviewCount = 1,
                downloadCount = "1",
                sizeMb = entity.sizeMb,
                version = entity.version,
                iconVectorName = entity.iconVectorName,
                accentColorHex = entity.accentColorHex,
                sandboxType = when (entity.sandboxTemplate) {
                    "NEURAL_STUDIO" -> SandboxType.NEURAL_STUDIO
                    "QUANTUM_VPN" -> SandboxType.QUANTUM_VPN
                    "OVERCLOCK_OPTIMIZER" -> SandboxType.OVERCLOCK_OPTIMIZER
                    "CHRONO_RACER" -> SandboxType.CHRONO_RACER
                    "BIOSYNC_METRICS" -> SandboxType.BIOSYNC_METRICS
                    "HOLO_CANVAS" -> SandboxType.HOLO_CANVAS
                    "VORTEX_SYNTH" -> SandboxType.VORTEX_SYNTH
                    else -> SandboxType.APEX_TERMINAL
                },
                isCustomCreated = true
            )
        }
        SampleAppData.flagshipApps + customApps
    }

    val installedAppsFlow: Flow<List<InstalledAppEntity>> = appDao.getInstalledApps()

    val wishlistFlow: Flow<List<WishlistEntity>> = appDao.getWishlist()

    val subscriptionFlow: Flow<SubscriptionEntity?> = appDao.getSubscription()

    val searchHistoryFlow: Flow<List<SearchHistoryEntity>> = appDao.getSearchHistory()

    fun getReviewsForApp(appId: String): Flow<List<AppReviewEntity>> =
        appDao.getReviewsForApp(appId)

    fun isAppInstalled(appId: String): Flow<Boolean> =
        appDao.isAppInstalled(appId)

    fun isWishlisted(appId: String): Flow<Boolean> =
        appDao.isWishlisted(appId)

    suspend fun installApp(app: AppItem) {
        appDao.installApp(
            InstalledAppEntity(
                id = app.id,
                installedAt = System.currentTimeMillis(),
                sizeMb = app.sizeMb,
                version = app.version,
                isCustomCreated = app.isCustomCreated
            )
        )
    }

    suspend fun uninstallApp(appId: String) {
        appDao.uninstallApp(appId)
    }

    suspend fun toggleWishlist(appId: String, currentWishlisted: Boolean) {
        if (currentWishlisted) {
            appDao.removeFromWishlist(appId)
        } else {
            appDao.addToWishlist(WishlistEntity(appId = appId))
        }
    }

    suspend fun addReview(appId: String, authorName: String, rating: Int, comment: String) {
        appDao.insertReview(
            AppReviewEntity(
                appId = appId,
                authorName = authorName,
                rating = rating,
                comment = comment,
                helpfulLikes = 0
            )
        )
    }

    suspend fun likeReview(reviewId: Long) {
        appDao.incrementHelpful(reviewId)
    }

    suspend fun createCustomApp(
        title: String,
        tagline: String,
        description: String,
        category: Category,
        packageId: String,
        version: String,
        accentColorHex: Long,
        iconVectorName: String,
        sandboxTemplate: String
    ): String {
        val id = "custom_${System.currentTimeMillis()}"
        val entity = CustomAppEntity(
            id = id,
            title = title,
            tagline = tagline,
            description = description,
            category = category.name,
            packageId = packageId,
            version = version,
            accentColorHex = accentColorHex,
            iconVectorName = iconVectorName,
            sandboxTemplate = sandboxTemplate,
            sizeMb = 12.8
        )
        appDao.insertCustomApp(entity)
        // Automatically install creator app so it can immediately be tested
        appDao.installApp(
            InstalledAppEntity(
                id = id,
                installedAt = System.currentTimeMillis(),
                sizeMb = 12.8,
                version = version,
                isCustomCreated = true
            )
        )
        return id
    }

    suspend fun deleteCustomApp(id: String) {
        appDao.deleteCustomApp(id)
        appDao.uninstallApp(id)
        appDao.removeFromWishlist(id)
    }

    suspend fun activateSubscription(
        tier: SubscriptionTier,
        paymentMethod: String,
        transactionId: String
    ) {
        val durationDays = when (tier) {
            SubscriptionTier.TRIAL_60_DAYS -> 60L
            SubscriptionTier.MONTHLY_PLAN -> 30L
            SubscriptionTier.PRO_PLAN -> 90L
            SubscriptionTier.STANDARD_FULL_OMNI -> 180L
            SubscriptionTier.YEARLY_PLAN -> 365L
            SubscriptionTier.ELITE_LIFETIME_OMEGA,
            SubscriptionTier.UNLIMITED_NEURAL_QUANTUM -> 365000L // 1,000 years!
        }
        val activatedAt = System.currentTimeMillis()
        val expiresAt = activatedAt + (durationDays * 24L * 60 * 60 * 1000)

        appDao.saveSubscription(
            SubscriptionEntity(
                id = 1,
                tierId = tier.tierId,
                planName = tier.title,
                paymentMethod = paymentMethod,
                transactionId = transactionId,
                activatedAt = activatedAt,
                expiresAt = expiresAt
            )
        )
    }

    suspend fun addSearchQuery(query: String) {
        if (query.isNotBlank()) {
            appDao.insertSearchQuery(SearchHistoryEntity(query.trim()))
        }
    }

    suspend fun deleteSearchQuery(query: String) {
        appDao.deleteSearchQuery(query)
    }

    suspend fun clearSearchHistory() {
        appDao.clearSearchHistory()
    }
}
