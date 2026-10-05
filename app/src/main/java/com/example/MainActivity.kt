package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.AppDatabase
import com.example.data.repository.AppRepository
import com.example.ui.components.CyberBottomNav
import com.example.ui.components.CyberTopBar
import com.example.ui.screens.AppDetailScreen
import com.example.ui.screens.AppSandboxModal
import com.example.ui.screens.AttributionLegalScreen
import com.example.ui.screens.CreatorStudioScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.screens.InstalledManagerScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SubscriptionHubScreen
import com.example.ui.theme.AX01AppTheme
import com.example.ui.theme.CyberDarkBg
import com.example.viewmodel.MainTab
import com.example.viewmodel.StoreViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getInstance(applicationContext)
        val repository = AppRepository(database.appDao())

        setContent {
            AX01AppTheme {
                val viewModel: StoreViewModel = viewModel { StoreViewModel(repository) }
                AX01StoreApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun AX01StoreApp(
    viewModel: StoreViewModel,
    modifier: Modifier = Modifier
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val selectedAppId by viewModel.selectedAppId.collectAsState()
    val activeSandboxApp by viewModel.activeSandboxApp.collectAsState()

    val allApps by viewModel.allApps.collectAsState()
    val installedApps by viewModel.installedApps.collectAsState()
    val wishlist by viewModel.wishlist.collectAsState()
    val subscription by viewModel.subscription.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val downloadingApps by viewModel.downloadingApps.collectAsState()
    val cacheMessage by viewModel.cachePurgedMessage.collectAsState()
    val axCredits by viewModel.axCredits.collectAsState()
    val dailyStreakClaimed by viewModel.dailyStreakClaimed.collectAsState()

    val installedIds = remember(installedApps) { installedApps.map { it.id }.toSet() }
    val wishlistIds = remember(wishlist) { wishlist.toSet() }
    val selectedApp = remember(selectedAppId, allApps) {
        allApps.firstOrNull { it.id == selectedAppId }
    }
    val customApps = remember(allApps) {
        allApps.filter { it.isCustomCreated }
    }

    // Hardware Back Button Navigation
    if (activeSandboxApp != null) {
        BackHandler {
            viewModel.closeSandbox()
        }
    } else if (selectedAppId != null) {
        BackHandler {
            viewModel.selectApp(null)
        }
    } else if (currentTab != MainTab.DISCOVER) {
        BackHandler {
            viewModel.selectTab(MainTab.DISCOVER)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            if (selectedApp == null) {
                CyberTopBar(
                    activePlanTitle = subscription?.planName,
                    axCredits = axCredits,
                    onPlanClick = { viewModel.selectTab(MainTab.SUBSCRIPTION) },
                    onAttributionClick = { viewModel.selectTab(MainTab.LEGAL_ATTRIBUTION) }
                )
            }
        },
        bottomBar = {
            if (selectedApp == null) {
                CyberBottomNav(
                    currentTab = currentTab,
                    onTabSelected = { viewModel.selectTab(it) }
                )
            }
        },
        containerColor = CyberDarkBg
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CyberDarkBg)
                .padding(innerPadding)
        ) {
            if (selectedApp != null) {
                AppDetailScreen(
                    app = selectedApp,
                    isInstalled = installedIds.contains(selectedApp.id),
                    isWishlisted = wishlistIds.contains(selectedApp.id),
                    downloadProgress = downloadingApps[selectedApp.id],
                    viewModel = viewModel,
                    onBack = { viewModel.selectApp(null) },
                    onInstallClick = { viewModel.startDownload(selectedApp) },
                    onOpenSandboxClick = { viewModel.openSandbox(selectedApp) },
                    onUninstallClick = { viewModel.uninstallApp(selectedApp.id) },
                    onWishlistToggle = { viewModel.toggleWishlist(selectedApp.id) }
                )
            } else {
                when (currentTab) {
                    MainTab.DISCOVER -> {
                        DiscoverScreen(
                            apps = allApps,
                            installedIds = installedIds,
                            wishlistIds = wishlistIds,
                            downloadingApps = downloadingApps,
                            selectedCategory = selectedCategory,
                            dailyStreakClaimed = dailyStreakClaimed,
                            onCategorySelect = { viewModel.selectCategory(it) },
                            onAppClick = { viewModel.selectApp(it) },
                            onInstallClick = { viewModel.startDownload(it) },
                            onOpenClick = { viewModel.openSandbox(it) },
                            onWishlistClick = { viewModel.toggleWishlist(it) },
                            onClaimStreak = { viewModel.claimDailyStreak() }
                        )
                    }

                    MainTab.SEARCH -> {
                        SearchScreen(
                            query = searchQuery,
                            allApps = allApps,
                            searchHistory = searchHistory,
                            installedIds = installedIds,
                            wishlistIds = wishlistIds,
                            downloadingApps = downloadingApps,
                            onQueryChange = { viewModel.updateSearchQuery(it) },
                            onSubmitSearch = { viewModel.submitSearch(it) },
                            onDeleteHistoryItem = { viewModel.deleteSearchQuery(it) },
                            onClearHistory = { viewModel.clearSearchHistory() },
                            onAppClick = { viewModel.selectApp(it) },
                            onInstallClick = { viewModel.startDownload(it) },
                            onOpenClick = { viewModel.openSandbox(it) },
                            onWishlistClick = { viewModel.toggleWishlist(it) }
                        )
                    }

                    MainTab.CREATOR -> {
                        CreatorStudioScreen(
                            customApps = customApps,
                            onCreateApp = { title, tag, desc, cat, pkg, ver, col, icon, template, onDone ->
                                viewModel.createAndDeployCustomApp(
                                    title, tag, desc, cat, pkg, ver, col, icon, template, onDone
                                )
                            },
                            onDeleteCustomApp = { viewModel.deleteCustomApp(it) },
                            onOpenSandbox = { viewModel.openSandbox(it) }
                        )
                    }

                    MainTab.STORAGE -> {
                        InstalledManagerScreen(
                            installedEntities = installedApps,
                            allApps = allApps,
                            wishlistIds = wishlist,
                            cacheMessage = cacheMessage,
                            onPurgeCache = { viewModel.purgeCache() },
                            onUpdateAll = { viewModel.updateAllApps() },
                            onLaunchSandbox = { viewModel.openSandbox(it) },
                            onUninstallApp = { viewModel.uninstallApp(it) },
                            onInstallWishlistApp = { viewModel.startDownload(it) },
                            onAppDetail = { viewModel.selectApp(it) }
                        )
                    }

                    MainTab.SUBSCRIPTION -> {
                        SubscriptionHubScreen(
                            currentSubscription = subscription,
                            onActivatePlan = { tier, method, txId ->
                                viewModel.activateSubscription(tier, method, txId)
                            }
                        )
                    }

                    MainTab.LEGAL_ATTRIBUTION -> {
                        AttributionLegalScreen()
                    }
                }
            }

            // Interactive Live App Sandbox Modal Overlay
            activeSandboxApp?.let { app ->
                AppSandboxModal(
                    app = app,
                    onClose = { viewModel.closeSandbox() }
                )
            }
        }
    }
}
