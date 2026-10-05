package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppItem
import com.example.model.InstallState
import com.example.model.SandboxType
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberBorderGlow
import com.example.ui.theme.CyberCard
import com.example.ui.theme.CyberCardElevated
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.NeonRose
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.DownloadProgress
import com.example.viewmodel.MainTab

@Composable
fun CyberTopBar(
    activePlanTitle: String?,
    axCredits: Int,
    onPlanClick: () -> Unit,
    onAttributionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CyberDarkBg,
        border = BorderStroke(1.dp, CyberBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Brand / Identity
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onAttributionClick() }
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                Brush.linearGradient(listOf(NeonCyan, NeonPurple))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "AX",
                            color = Color.Black,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "AX-01",
                                color = NeonCyan,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Monospace
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "STORE",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                        Text(
                            text = "RZK Softoware Enclave",
                            color = TextMuted,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                // Status & Plan pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Credit balance
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(CyberCardElevated)
                            .border(1.dp, CyberBorder, RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ElectricBolt,
                                contentDescription = "AX Credits",
                                tint = NeonAmber,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "$axCredits AX",
                                color = NeonAmber,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    // Active Subscription badge
                    val planText = activePlanTitle ?: "Trial (60d)"
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(CyberCardElevated)
                            .border(1.dp, NeonCyan.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                            .clickable { onPlanClick() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .testTag("top_bar_plan_pill")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(NeonEmerald)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = planText,
                                color = NeonCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            // Slogan Banner Strip
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF0F172A))
                    .border(1.dp, CyberBorderGlow.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                    .clickable { onAttributionClick() }
                    .padding(horizontal = 8.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "\"শূণ্য থেকে শুরু শূন্য তেই শেষ\"",
                    color = NeonCyan.copy(alpha = 0.9f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "AI Quantum 1,000 Yr Security • 24h Feed",
                    color = TextMuted,
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun CyberBottomNav(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = CyberDarkBg,
        tonalElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, CyberBorder), RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
    ) {
        NavigationBarItem(
            selected = currentTab == MainTab.DISCOVER,
            onClick = { onTabSelected(MainTab.DISCOVER) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Discover") },
            label = { Text("Discover", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NeonCyan,
                selectedTextColor = NeonCyan,
                indicatorColor = CyberCardElevated,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_tab_discover")
        )

        NavigationBarItem(
            selected = currentTab == MainTab.SEARCH,
            onClick = { onTabSelected(MainTab.SEARCH) },
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NeonCyan,
                selectedTextColor = NeonCyan,
                indicatorColor = CyberCardElevated,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_tab_search")
        )

        NavigationBarItem(
            selected = currentTab == MainTab.CREATOR,
            onClick = { onTabSelected(MainTab.CREATOR) },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Creator") },
            label = { Text("Creator", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NeonPurple,
                selectedTextColor = NeonPurple,
                indicatorColor = CyberCardElevated,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_tab_creator")
        )

        NavigationBarItem(
            selected = currentTab == MainTab.STORAGE,
            onClick = { onTabSelected(MainTab.STORAGE) },
            icon = { Icon(Icons.Default.Storage, contentDescription = "Storage") },
            label = { Text("Enclave", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NeonEmerald,
                selectedTextColor = NeonEmerald,
                indicatorColor = CyberCardElevated,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_tab_storage")
        )

        NavigationBarItem(
            selected = currentTab == MainTab.SUBSCRIPTION,
            onClick = { onTabSelected(MainTab.SUBSCRIPTION) },
            icon = { Icon(Icons.Default.WorkspacePremium, contentDescription = "Pricing") },
            label = { Text("Tiers", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NeonAmber,
                selectedTextColor = NeonAmber,
                indicatorColor = CyberCardElevated,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_tab_subscription")
        )

        NavigationBarItem(
            selected = currentTab == MainTab.LEGAL_ATTRIBUTION,
            onClick = { onTabSelected(MainTab.LEGAL_ATTRIBUTION) },
            icon = { Icon(Icons.Default.Gavel, contentDescription = "Legal & RZK") },
            label = { Text("Legal", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = NeonRose,
                selectedTextColor = NeonRose,
                indicatorColor = CyberCardElevated,
                unselectedIconColor = TextMuted,
                unselectedTextColor = TextMuted
            ),
            modifier = Modifier.testTag("nav_tab_legal")
        )
    }
}

@Composable
fun AppCard(
    app: AppItem,
    isInstalled: Boolean,
    downloadProgress: DownloadProgress?,
    isWishlisted: Boolean,
    onAppClick: () -> Unit,
    onInstallClick: () -> Unit,
    onOpenClick: () -> Unit,
    onWishlistClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accentColor = Color(app.accentColorHex)

    Card(
        colors = CardDefaults.cardColors(containerColor = CyberCard),
        border = BorderStroke(1.dp, CyberBorder),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onAppClick() }
            .testTag("app_card_${app.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // App Icon Box
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    accentColor.copy(alpha = 0.35f),
                                    CyberSurface
                                )
                            )
                        )
                        .border(1.dp, accentColor.copy(alpha = 0.6f), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = app.title.take(2).uppercase(),
                        color = accentColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                // App Details
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = app.title,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (app.isCustomCreated) {
                            Spacer(modifier = Modifier.width(6.dp))
                            CyberPill(text = "CREATOR", color = NeonPurple)
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = app.tagline,
                        color = TextSecondary,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = NeonAmber,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = String.format("%.1f", app.rating),
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${app.sizeMb} MB",
                            color = TextMuted,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        CyberPill(text = app.category.displayName, color = Color(app.category.badgeColorHex))
                    }
                }

                // Wishlist icon
                IconButton(
                    onClick = onWishlistClick,
                    modifier = Modifier.size(36.dp).testTag("wishlist_btn_${app.id}")
                ) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted) NeonCyan else TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Download Progress or Action Row
            Spacer(modifier = Modifier.height(10.dp))
            if (downloadProgress != null) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "DOWNLOADING ${(downloadProgress.progress * 100).toInt()}%",
                            color = NeonCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "${downloadProgress.speedKbps} KB/s",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { downloadProgress.progress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = NeonCyan,
                        trackColor = CyberSurface,
                        strokeCap = StrokeCap.Round
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isInstalled) {
                        OutlinedButton(
                            onClick = onOpenClick,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = NeonEmerald
                            ),
                            border = BorderStroke(1.dp, NeonEmerald),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .height(34.dp)
                                .testTag("open_sandbox_${app.id}")
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("RUN SANDBOX", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = onInstallClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonCyan,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .height(34.dp)
                                .testTag("install_btn_${app.id}")
                        ) {
                            Icon(
                                Icons.Default.CloudDownload,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("GET MODULE", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CyberPill(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.15f))
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}
