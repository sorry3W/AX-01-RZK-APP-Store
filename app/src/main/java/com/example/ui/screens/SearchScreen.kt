package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppItem
import com.example.ui.components.AppCard
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCard
import com.example.ui.theme.CyberCardElevated
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.viewmodel.DownloadProgress

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    query: String,
    allApps: List<AppItem>,
    searchHistory: List<String>,
    installedIds: Set<String>,
    wishlistIds: Set<String>,
    downloadingApps: Map<String, DownloadProgress>,
    onQueryChange: (String) -> Unit,
    onSubmitSearch: (String) -> Unit,
    onDeleteHistoryItem: (String) -> Unit,
    onClearHistory: () -> Unit,
    onAppClick: (String) -> Unit,
    onInstallClick: (AppItem) -> Unit,
    onOpenClick: (AppItem) -> Unit,
    onWishlistClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val trendingKeywords = remember {
        listOf(
            "Quantum VPN", "Neural Studio", "Terminal", "Antigrav", "BioSync",
            "Optimizer", "Synthwave", "Holo Canvas", "Lattice Cryptography"
        )
    }

    val filteredApps = remember(query, allApps) {
        if (query.isBlank()) {
            emptyList()
        } else {
            val q = query.trim().lowercase()
            allApps.filter { app ->
                app.title.lowercase().contains(q) ||
                    app.tagline.lowercase().contains(q) ||
                    app.description.lowercase().contains(q) ||
                    app.developer.lowercase().contains(q) ||
                    app.category.displayName.lowercase().contains(q)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    ) {
        // Search Input Bar
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_text_input"),
            placeholder = {
                Text("Search cyber modules, AI tools, games...", color = TextMuted, fontSize = 13.sp)
            },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = NeonCyan)
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear", tint = TextMuted)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = CyberCard,
                unfocusedContainerColor = CyberCard,
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = CyberBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = NeonCyan
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (query.isBlank()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Trending Keywords
                item {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                "TRENDING KEYWORDS",
                                color = TextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            trendingKeywords.forEach { tag ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(CyberCardElevated)
                                        .border(1.dp, CyberBorder, RoundedCornerShape(6.dp))
                                        .clickable {
                                            onQueryChange(tag)
                                            onSubmitSearch(tag)
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "#$tag",
                                        color = NeonCyan,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }

                // Recent Searches from Room Database
                if (searchHistory.isNotEmpty()) {
                    item {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.History, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        "RECENT SEARCHES",
                                        color = TextSecondary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                TextButton(onClick = onClearHistory) {
                                    Text("Clear All", color = TextMuted, fontSize = 11.sp)
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                searchHistory.forEach { hist ->
                                    Row(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(CyberCard)
                                            .border(1.dp, CyberBorder, RoundedCornerShape(6.dp))
                                            .clickable {
                                                onQueryChange(hist)
                                                onSubmitSearch(hist)
                                            }
                                            .padding(start = 10.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(hist, color = TextPrimary, fontSize = 11.sp)
                                        IconButton(
                                            onClick = { onDeleteHistoryItem(hist) },
                                            modifier = Modifier.size(22.dp)
                                        ) {
                                            Icon(Icons.Default.Close, contentDescription = null, tint = TextMuted, modifier = Modifier.size(12.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Search Results
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SEARCH RESULTS (${filteredApps.size})",
                    color = TextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (filteredApps.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("No matching quantum modules found.", color = TextSecondary, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Try searching for 'VPN', 'Terminal', or 'Neural'.", color = TextMuted, fontSize = 11.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredApps, key = { it.id }) { app ->
                        AppCard(
                            app = app,
                            isInstalled = installedIds.contains(app.id),
                            downloadProgress = downloadingApps[app.id],
                            isWishlisted = wishlistIds.contains(app.id),
                            onAppClick = { onAppClick(app.id) },
                            onInstallClick = {
                                onSubmitSearch(query)
                                onInstallClick(app)
                            },
                            onOpenClick = { onOpenClick(app) },
                            onWishlistClick = { onWishlistClick(app.id) }
                        )
                    }
                }
            }
        }
    }
}
