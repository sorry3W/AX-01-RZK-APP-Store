package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppItem
import com.example.model.Category
import com.example.ui.components.AppCard
import com.example.ui.components.CyberPill
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCard
import com.example.ui.theme.CyberCardElevated
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.DownloadProgress
import kotlinx.coroutines.delay

@Composable
fun DiscoverScreen(
    apps: List<AppItem>,
    installedIds: Set<String>,
    wishlistIds: Set<String>,
    downloadingApps: Map<String, DownloadProgress>,
    selectedCategory: Category,
    dailyStreakClaimed: Boolean,
    onCategorySelect: (Category) -> Unit,
    onAppClick: (String) -> Unit,
    onInstallClick: (AppItem) -> Unit,
    onOpenClick: (AppItem) -> Unit,
    onWishlistClick: (String) -> Unit,
    onClaimStreak: () -> Unit,
    modifier: Modifier = Modifier
) {
    val featuredApps = remember(apps) { apps.filter { it.isFeatured } }
    val appOfTheDay = remember(apps) { apps.firstOrNull { it.isAppOfTheDay } ?: apps.firstOrNull() }
    val filteredApps = remember(apps, selectedCategory) {
        if (selectedCategory == Category.ALL) apps else apps.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Daily Streak HUD
        item {
            DailyStreakCard(
                claimed = dailyStreakClaimed,
                onClaim = onClaimStreak
            )
        }

        // Hero Carousel
        if (featuredApps.isNotEmpty()) {
            item {
                HeroCarousel(
                    featuredApps = featuredApps,
                    isInstalled = { installedIds.contains(it.id) },
                    onAppClick = { onAppClick(it.id) },
                    onInstallClick = { onInstallClick(it) },
                    onOpenClick = { onOpenClick(it) }
                )
            }
        }

        // Category Filter Chips
        item {
            CategoryFilterBar(
                selectedCategory = selectedCategory,
                onCategorySelect = onCategorySelect
            )
        }

        // App of the Day Spotlight
        if (appOfTheDay != null && selectedCategory == Category.ALL) {
            item {
                AppOfTheDayCard(
                    app = appOfTheDay,
                    isInstalled = installedIds.contains(appOfTheDay.id),
                    onAppClick = { onAppClick(appOfTheDay.id) },
                    onInstallClick = { onInstallClick(appOfTheDay) },
                    onOpenClick = { onOpenClick(appOfTheDay) }
                )
            }
        }

        // Trending / Category Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (selectedCategory == Category.ALL) "QUANTUM MATRIX APPS" else selectedCategory.displayName.uppercase(),
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Text(
                    text = "${filteredApps.size} Modules",
                    color = TextMuted,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        // List of App Cards
        items(filteredApps, key = { it.id }) { app ->
            AppCard(
                app = app,
                isInstalled = installedIds.contains(app.id),
                downloadProgress = downloadingApps[app.id],
                isWishlisted = wishlistIds.contains(app.id),
                onAppClick = { onAppClick(app.id) },
                onInstallClick = { onInstallClick(app) },
                onOpenClick = { onOpenClick(app) },
                onWishlistClick = { onWishlistClick(app.id) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HeroCarousel(
    featuredApps: List<AppItem>,
    isInstalled: (AppItem) -> Boolean,
    onAppClick: (AppItem) -> Unit,
    onInstallClick: (AppItem) -> Unit,
    onOpenClick: (AppItem) -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(featuredApps) {
        while (featuredApps.isNotEmpty()) {
            delay(5000)
            currentIndex = (currentIndex + 1) % featuredApps.size
        }
    }

    if (featuredApps.isEmpty()) return
    val currentApp = featuredApps[currentIndex]
    val accentColor = Color(currentApp.accentColorHex)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable { onAppClick(currentApp) }
            .testTag("hero_carousel"),
        colors = CardDefaults.cardColors(containerColor = CyberCardElevated),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.8f))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background subtle gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                accentColor.copy(alpha = 0.25f),
                                Color(0xFF0B1120)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CyberPill(text = "FLAGSHIP SPOTLIGHT", color = accentColor)
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        featuredApps.indices.forEach { idx ->
                            Box(
                                modifier = Modifier
                                    .size(if (idx == currentIndex) 16.dp else 6.dp, 6.dp)
                                    .clip(CircleShape)
                                    .background(if (idx == currentIndex) accentColor else TextMuted)
                            )
                        }
                    }
                }

                Column {
                    Text(
                        text = currentApp.title,
                        color = TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = currentApp.tagline,
                        color = TextSecondary,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = NeonAmber, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", currentApp.rating),
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${currentApp.downloadCount} DLs",
                            color = TextMuted,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    if (isInstalled(currentApp)) {
                        Button(
                            onClick = { onOpenClick(currentApp) },
                            colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald, contentColor = Color.Black),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("RUN SANDBOX", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = { onInstallClick(currentApp) },
                            colors = ButtonDefaults.buttonColors(containerColor = accentColor, contentColor = Color.Black),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text("GET NOW", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryFilterBar(
    selectedCategory: Category,
    onCategorySelect: (Category) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Category.entries.forEach { cat ->
            val isSelected = cat == selectedCategory
            val color = Color(cat.badgeColorHex)

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) color.copy(alpha = 0.25f) else CyberCard)
                    .border(1.dp, if (isSelected) color else CyberBorder, RoundedCornerShape(8.dp))
                    .clickable { onCategorySelect(cat) }
                    .padding(horizontal = 12.dp, vertical = 7.dp)
            ) {
                Text(
                    text = cat.displayName,
                    color = if (isSelected) color else TextSecondary,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun AppOfTheDayCard(
    app: AppItem,
    isInstalled: Boolean,
    onAppClick: () -> Unit,
    onInstallClick: () -> Unit,
    onOpenClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAppClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF141E34)),
        border = BorderStroke(1.dp, NeonAmber.copy(alpha = 0.7f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Brush.linearGradient(listOf(NeonAmber.copy(alpha = 0.4f), CyberSurface)))
                    .border(1.dp, NeonAmber, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("★", color = NeonAmber, fontSize = 24.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                CyberPill(text = "APP OF THE DAY", color = NeonAmber)
                Spacer(modifier = Modifier.height(3.dp))
                Text(app.title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(app.tagline, color = TextSecondary, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (isInstalled) {
                Button(
                    onClick = onOpenClick,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald, contentColor = Color.Black),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("OPEN", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = onInstallClick,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonAmber, contentColor = Color.Black),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text("GET", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun DailyStreakCard(
    claimed: Boolean,
    onClaim: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CyberCard),
        border = BorderStroke(1.dp, CyberBorder),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ElectricBolt,
                    contentDescription = null,
                    tint = NeonAmber,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = if (claimed) "DAILY STREAK BONUS CLAIMED" else "DAILY QUANTUM REWARD READY",
                        color = if (claimed) NeonEmerald else NeonAmber,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = if (claimed) "+140 AX Credits & +150 XP added" else "Claim +140 AX Credits & +150 XP bonus",
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                }
            }

            if (!claimed) {
                Button(
                    onClick = onClaim,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonAmber, contentColor = Color.Black),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.height(30.dp).testTag("claim_daily_streak_btn")
                ) {
                    Text("CLAIM", fontSize = 10.sp, fontWeight = FontWeight.Black)
                }
            }
        }
    }
}
