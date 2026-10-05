package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.entity.AppReviewEntity
import com.example.data.local.entity.InstalledAppEntity
import com.example.data.local.entity.SubscriptionEntity
import com.example.data.repository.AppRepository
import com.example.model.AppItem
import com.example.model.Category
import com.example.model.InstallState
import com.example.model.SubscriptionTier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainTab {
    DISCOVER,
    SEARCH,
    CREATOR,
    STORAGE,
    SUBSCRIPTION,
    LEGAL_ATTRIBUTION
}

data class DownloadProgress(
    val appId: String,
    val progress: Float,
    val speedKbps: Int
)

class StoreViewModel(private val repository: AppRepository) : ViewModel() {

    // Tab state
    private val _currentTab = MutableStateFlow(MainTab.DISCOVER)
    val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

    // Selected app for detail screen
    private val _selectedAppId = MutableStateFlow<String?>(null)
    val selectedAppId: StateFlow<String?> = _selectedAppId.asStateFlow()

    // Active app running in interactive sandbox
    private val _activeSandboxApp = MutableStateFlow<AppItem?>(null)
    val activeSandboxApp: StateFlow<AppItem?> = _activeSandboxApp.asStateFlow()

    // Category filter for Discover screen
    private val _selectedCategory = MutableStateFlow(Category.ALL)
    val selectedCategory: StateFlow<Category> = _selectedCategory.asStateFlow()

    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Active downloading states mapped by appId
    private val _downloadingApps = MutableStateFlow<Map<String, DownloadProgress>>(emptyMap())
    val downloadingApps: StateFlow<Map<String, DownloadProgress>> = _downloadingApps.asStateFlow()

    // Cache purge status message
    private val _cachePurgedMessage = MutableStateFlow<String?>(null)
    val cachePurgedMessage: StateFlow<String?> = _cachePurgedMessage.asStateFlow()

    // AX Credits & XP
    private val _axCredits = MutableStateFlow(2450)
    val axCredits: StateFlow<Int> = _axCredits.asStateFlow()

    private val _userXp = MutableStateFlow(840)
    val userXp: StateFlow<Int> = _userXp.asStateFlow()

    private val _dailyStreakClaimed = MutableStateFlow(false)
    val dailyStreakClaimed: StateFlow<Boolean> = _dailyStreakClaimed.asStateFlow()

    // Catalog flow
    val allApps: StateFlow<List<AppItem>> = repository.allAppsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val installedApps: StateFlow<List<InstalledAppEntity>> = repository.installedAppsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wishlist: StateFlow<List<String>> = repository.wishlistFlow
        .combine(MutableStateFlow(Unit)) { list, _ -> list.map { it.appId } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val subscription: StateFlow<SubscriptionEntity?> = repository.subscriptionFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val searchHistory: StateFlow<List<String>> = repository.searchHistoryFlow
        .combine(MutableStateFlow(Unit)) { list, _ -> list.map { it.query } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectTab(tab: MainTab) {
        _currentTab.value = tab
        // Clear selected app when changing tabs
        _selectedAppId.value = null
    }

    fun selectApp(appId: String?) {
        _selectedAppId.value = appId
    }

    fun openSandbox(app: AppItem) {
        _activeSandboxApp.value = app
    }

    fun closeSandbox() {
        _activeSandboxApp.value = null
    }

    fun selectCategory(category: Category) {
        _selectedCategory.value = category
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun submitSearch(query: String) {
        _searchQuery.value = query
        if (query.isNotBlank()) {
            viewModelScope.launch {
                repository.addSearchQuery(query)
            }
        }
    }

    fun deleteSearchQuery(query: String) {
        viewModelScope.launch {
            repository.deleteSearchQuery(query)
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            repository.clearSearchHistory()
        }
    }

    fun toggleWishlist(appId: String) {
        viewModelScope.launch {
            val isCurrent = wishlist.value.contains(appId)
            repository.toggleWishlist(appId, isCurrent)
        }
    }

    fun startDownload(app: AppItem) {
        if (_downloadingApps.value.containsKey(app.id)) return

        viewModelScope.launch {
            var progress = 0f
            while (progress < 1f) {
                progress += 0.15f
                val clamped = progress.coerceAtMost(1f)
                val speed = (1200..1850).random()
                _downloadingApps.value = _downloadingApps.value + (app.id to DownloadProgress(app.id, clamped, speed))
                delay(180)
            }
            delay(100)
            repository.installApp(app)
            _downloadingApps.value = _downloadingApps.value - app.id
            // Reward XP and Credits on installation!
            _axCredits.value += 50
            _userXp.value += 120
        }
    }

    fun uninstallApp(appId: String) {
        viewModelScope.launch {
            repository.uninstallApp(appId)
        }
    }

    fun updateAllApps() {
        viewModelScope.launch {
            val installed = installedApps.value
            val apps = allApps.value.filter { app -> installed.any { it.id == app.id } }
            for (app in apps) {
                startDownload(app)
                delay(300)
            }
        }
    }

    fun purgeCache() {
        viewModelScope.launch {
            delay(400)
            _cachePurgedMessage.value = "Purged 384 MB AX Enclave Cache successfully."
            delay(2500)
            _cachePurgedMessage.value = null
        }
    }

    fun claimDailyStreak() {
        if (!_dailyStreakClaimed.value) {
            _dailyStreakClaimed.value = true
            _axCredits.value += 140
            _userXp.value += 150
        }
    }

    fun createAndDeployCustomApp(
        title: String,
        tagline: String,
        description: String,
        category: Category,
        packageId: String,
        version: String,
        accentColorHex: Long,
        iconVectorName: String,
        sandboxTemplate: String,
        onCreated: (String) -> Unit
    ) {
        viewModelScope.launch {
            val newId = repository.createCustomApp(
                title = title,
                tagline = tagline,
                description = description,
                category = category,
                packageId = packageId,
                version = version,
                accentColorHex = accentColorHex,
                iconVectorName = iconVectorName,
                sandboxTemplate = sandboxTemplate
            )
            _axCredits.value += 200
            _userXp.value += 300
            onCreated(newId)
        }
    }

    fun deleteCustomApp(id: String) {
        viewModelScope.launch {
            repository.deleteCustomApp(id)
            if (_selectedAppId.value == id) {
                _selectedAppId.value = null
            }
        }
    }

    fun activateSubscription(
        tier: SubscriptionTier,
        paymentMethod: String,
        transactionId: String
    ) {
        viewModelScope.launch {
            repository.activateSubscription(tier, paymentMethod, transactionId)
            _axCredits.value += (tier.priceNumeric / 10).toInt().coerceAtLeast(500)
            _userXp.value += 500
        }
    }

    fun addReview(appId: String, author: String, rating: Int, comment: String) {
        viewModelScope.launch {
            repository.addReview(appId, author, rating, comment)
            _axCredits.value += 30
            _userXp.value += 60
        }
    }

    fun likeReview(reviewId: Long) {
        viewModelScope.launch {
            repository.likeReview(reviewId)
        }
    }

    fun getReviews(appId: String) = repository.getReviewsForApp(appId)
}
