package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entity.InstalledAppEntity
import com.example.model.AppItem
import com.example.ui.components.CyberPill
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCard
import com.example.ui.theme.CyberCardElevated
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.NeonAmber
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonEmerald
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.NeonRose
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun InstalledManagerScreen(
    installedEntities: List<InstalledAppEntity>,
    allApps: List<AppItem>,
    wishlistIds: List<String>,
    cacheMessage: String?,
    onPurgeCache: () -> Unit,
    onUpdateAll: () -> Unit,
    onLaunchSandbox: (AppItem) -> Unit,
    onUninstallApp: (String) -> Unit,
    onInstallWishlistApp: (AppItem) -> Unit,
    onAppDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val installedApps = remember(installedEntities, allApps) {
        allApps.filter { app -> installedEntities.any { it.id == app.id } }
    }
    val wishlistApps = remember(wishlistIds, allApps) {
        allApps.filter { app -> wishlistIds.contains(app.id) }
    }

    val totalInstalledMb = remember(installedApps) {
        installedApps.sumOf { it.sizeMb }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Enclave Storage Visualizer Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberCardElevated),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Storage, contentDescription = null, tint = NeonEmerald, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("AX-01 ENCLAVE STORAGE", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                        }
                        Text("${String.format("%.1f", totalInstalledMb + 384.0)} MB USED", color = NeonCyan, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Multi-segment progress bar simulation
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(CyberSurface)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(0.35f)
                                .fillMaxSize()
                                .background(NeonCyan)
                        )
                        Box(
                            modifier = Modifier
                                .weight(0.25f)
                                .fillMaxSize()
                                .background(NeonPurple)
                        )
                        Box(
                            modifier = Modifier
                                .weight(0.15f)
                                .fillMaxSize()
                                .background(NeonAmber)
                        )
                        Box(
                            modifier = Modifier
                                .weight(0.25f)
                                .fillMaxSize()
                                .background(CyberBorder)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Legend
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        LegendItem(label = "Binaries", value = "${String.format("%.1f", totalInstalledMb)}M", color = NeonCyan)
                        LegendItem(label = "AX Cache", value = "384M", color = NeonPurple)
                        LegendItem(label = "Neural Data", value = "120M", color = NeonAmber)
                        LegendItem(label = "Free Enclave", value = "28.4G", color = TextMuted)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Storage Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onPurgeCache,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonCyan),
                            border = BorderStroke(1.dp, NeonCyan),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).height(40.dp).testTag("purge_cache_btn")
                        ) {
                            Icon(Icons.Default.CleaningServices, contentDescription = null, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("PURGE CACHE", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = onUpdateAll,
                            colors = ButtonDefaults.buttonColors(containerColor = CyberCard, contentColor = NeonEmerald),
                            border = BorderStroke(1.dp, NeonEmerald),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f).height(40.dp).testTag("update_all_btn")
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("UPDATE ALL", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (cacheMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(cacheMessage, color = NeonEmerald, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // Section: Installed Applications
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "INSTALLED ENCLAVES (${installedApps.size})",
                    color = TextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        if (installedApps.isEmpty()) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberCard),
                    border = BorderStroke(1.dp, CyberBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.padding(24.dp), contentAlignment = Alignment.Center) {
                        Text("No installed cyber modules yet. Browse Discover tab to get modules.", color = TextMuted, fontSize = 12.sp)
                    }
                }
            }
        } else {
            items(installedApps, key = { it.id }) { app ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberCard),
                    border = BorderStroke(1.dp, CyberBorder),
                    modifier = Modifier.fillMaxWidth().clickable { onAppDetail(app.id) }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(app.accentColorHex).copy(alpha = 0.25f))
                                    .border(1.dp, Color(app.accentColorHex), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(app.title.take(2).uppercase(), color = Color(app.accentColorHex), fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(app.title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("${app.sizeMb} MB", color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    CyberPill(text = "v${app.version}", color = NeonEmerald)
                                }
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = { onLaunchSandbox(app) },
                                colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald, contentColor = Color.Black),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.height(32.dp).testTag("enclave_launch_${app.id}")
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("RUN", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            IconButton(onClick = { onUninstallApp(app.id) }, modifier = Modifier.size(32.dp)) {
                                Icon(Icons.Default.Delete, contentDescription = "Uninstall", tint = NeonRose, modifier = Modifier.size(16.dp))
                            }
                        }
                    }
                }
            }
        }

        // Section: Bookmarked Wishlist
        if (wishlistApps.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Bookmark, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "SAVED BOOKMARKS (${wishlistApps.size})",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            items(wishlistApps, key = { "wish_${it.id}" }) { app ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberCardElevated),
                    border = BorderStroke(1.dp, CyberBorder),
                    modifier = Modifier.fillMaxWidth().clickable { onAppDetail(app.id) }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(app.title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(app.tagline, color = TextSecondary, fontSize = 11.sp)
                        }

                        Button(
                            onClick = { onInstallWishlistApp(app) },
                            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text("GET", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun LegendItem(label: String, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(7.dp).clip(CircleShape).background(color))
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(label, color = TextMuted, fontSize = 9.sp)
            Text(value, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        }
    }
}
