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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.entity.AppReviewEntity
import com.example.model.AppItem
import com.example.ui.components.CyberPill
import com.example.ui.theme.CyberBorder
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
import com.example.viewmodel.StoreViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun AppDetailScreen(
    app: AppItem,
    isInstalled: Boolean,
    isWishlisted: Boolean,
    downloadProgress: DownloadProgress?,
    viewModel: StoreViewModel,
    onBack: () -> Unit,
    onInstallClick: () -> Unit,
    onOpenSandboxClick: () -> Unit,
    onUninstallClick: () -> Unit,
    onWishlistToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accentColor = Color(app.accentColorHex)
    val reviews by viewModel.getReviews(app.id).collectAsState(initial = emptyList())
    var showReviewDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberDarkBg)
    ) {
        // App Detail Header Nav
        Surface(
            color = CyberSurface,
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.testTag("detail_back_btn")) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                }

                Text(
                    text = app.title,
                    color = TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = onWishlistToggle, modifier = Modifier.testTag("detail_wishlist_btn")) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Wishlist",
                        tint = if (isWishlisted) NeonCyan else TextMuted
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(6.dp))
            }

            // Hero App Header Card
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(accentColor.copy(alpha = 0.4f), CyberSurface)
                                )
                            )
                            .border(2.dp, accentColor, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = app.title.take(2).uppercase(),
                            color = accentColor,
                            fontWeight = FontWeight.Black,
                            fontSize = 26.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = app.title,
                            color = TextPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = app.developer,
                            color = NeonCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CyberPill(text = app.category.displayName, color = Color(app.category.badgeColorHex))
                            Spacer(modifier = Modifier.width(6.dp))
                            CyberPill(text = "v${app.version}", color = TextSecondary)
                        }
                    }
                }
            }

            // Key Metrics Bar
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberCard),
                    border = BorderStroke(1.dp, CyberBorder),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = String.format("%.1f", app.rating),
                                    color = TextPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Icon(Icons.Default.Star, contentDescription = null, tint = NeonAmber, modifier = Modifier.size(14.dp))
                            }
                            Text("${app.reviewCount} Reviews", color = TextMuted, fontSize = 10.sp)
                        }

                        Box(modifier = Modifier.width(1.dp).height(24.dp).background(CyberBorder))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = app.downloadCount,
                                color = TextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text("Downloads", color = TextMuted, fontSize = 10.sp)
                        }

                        Box(modifier = Modifier.width(1.dp).height(24.dp).background(CyberBorder))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${app.sizeMb} MB",
                                color = TextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text("Package Size", color = TextMuted, fontSize = 10.sp)
                        }

                        Box(modifier = Modifier.width(1.dp).height(24.dp).background(CyberBorder))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "LEVEL-Ω",
                                color = NeonEmerald,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text("Enclave Audit", color = TextMuted, fontSize = 10.sp)
                        }
                    }
                }
            }

            // Primary Action Buttons
            item {
                if (downloadProgress != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(CyberCardElevated)
                            .border(1.dp, NeonCyan, RoundedCornerShape(10.dp))
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "INSTALLING MATRIX: ${(downloadProgress.progress * 100).toInt()}%",
                                color = NeonCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                "${downloadProgress.speedKbps} KB/s",
                                color = TextSecondary,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { downloadProgress.progress },
                            modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                            color = NeonCyan,
                            trackColor = CyberSurface
                        )
                    }
                } else if (isInstalled) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onOpenSandboxClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonEmerald,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("run_sandbox_btn")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("RUN SANDBOX", fontWeight = FontWeight.Black, fontSize = 13.sp)
                        }

                        OutlinedButton(
                            onClick = onUninstallClick,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonRose),
                            border = BorderStroke(1.dp, NeonRose),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.height(48.dp).testTag("uninstall_btn")
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("UNINSTALL", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                } else {
                    Button(
                        onClick = onInstallClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("install_action_btn")
                    ) {
                        Icon(Icons.Default.CloudDownload, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("INSTALL QUANTUM MODULE (${app.sizeMb} MB)", fontWeight = FontWeight.Black, fontSize = 13.sp)
                    }
                }
            }

            // Description & Tagline
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberCard),
                    border = BorderStroke(1.dp, CyberBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("ABOUT THIS MODULE", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(app.description, color = TextPrimary, fontSize = 13.sp, lineHeight = 19.sp)

                        if (app.features.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("CORE CAPABILITIES", color = NeonCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                            Spacer(modifier = Modifier.height(6.dp))
                            app.features.forEach { feat ->
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = NeonEmerald, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(feat, color = TextSecondary, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // AX-Shield Zero Trust Security Audit
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF071B18)),
                    border = BorderStroke(1.dp, NeonEmerald.copy(alpha = 0.6f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Security, contentDescription = null, tint = NeonEmerald, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("AX-01 ZERO-TRUST SECURITY AUDIT", color = NeonEmerald, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Certified by Sultan Mahmud Sumon (Lead Architect, rzk). Verified for automated 24-hour data feed synchronization and protected under the 1,000-year quantum cryptographic standard.",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Enclave Isolation: COMPLETE • Memory Shield: ACTIVATED",
                            color = NeonEmerald,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            // Technical Specifications
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberCard),
                    border = BorderStroke(1.dp, CyberBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("TECHNICAL SPECIFICATIONS", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                        Spacer(modifier = Modifier.height(8.dp))

                        listOf(
                            "Package ID" to "com.ax01.module.${app.id}",
                            "Module Version" to app.version,
                            "Architecture" to "ARM64-v8a / Quantum-NPU",
                            "Data Feed Sync" to "Every 24 Hours",
                            "App Life Span" to "1,000 Years Quantum Guarantee"
                        ).forEach { (label, value) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(label, color = TextMuted, fontSize = 11.sp)
                                Text(value, color = TextPrimary, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                            }
                        }
                    }
                }
            }

            // Reviews & Community Feedback
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "COMMUNITY REVIEWS (${reviews.size + app.reviewCount})",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    TextButton(onClick = { showReviewDialog = true }) {
                        Icon(Icons.Default.RateReview, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Write Review", color = NeonCyan, fontSize = 11.sp)
                    }
                }
            }

            // Default review showcase
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberCardElevated),
                    border = BorderStroke(1.dp, CyberBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("CyberOperator_99", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Row {
                                repeat(5) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = NeonAmber, modifier = Modifier.size(12.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Flawless performance in the sandbox. The 24h automated sync keeps the cyber telemetry perfectly updated.",
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Custom submitted reviews from Room
            items(reviews) { rev ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberCardElevated),
                    border = BorderStroke(1.dp, CyberBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(rev.authorName, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Row {
                                repeat(rev.rating) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = NeonAmber, modifier = Modifier.size(12.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(rev.comment, color = TextSecondary, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { viewModel.likeReview(rev.id) }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Default.ThumbUp, contentDescription = "Like", tint = NeonCyan, modifier = Modifier.size(12.dp))
                            }
                            Spacer(modifier = Modifier.width(2.dp))
                            Text("${rev.helpfulLikes}", color = TextMuted, fontSize = 10.sp)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        // Write Review Dialog
        if (showReviewDialog) {
            WriteReviewDialog(
                appTitle = app.title,
                onDismiss = { showReviewDialog = false },
                onSubmit = { author, rating, comment ->
                    viewModel.addReview(app.id, author, rating, comment)
                    showReviewDialog = false
                }
            )
        }
    }
}

@Composable
fun WriteReviewDialog(
    appTitle: String,
    onDismiss: () -> Unit,
    onSubmit: (String, Int, String) -> Unit
) {
    var authorName by remember { mutableStateOf("CyberUser") }
    var rating by remember { mutableIntStateOf(5) }
    var comment by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberDarkBg),
            border = BorderStroke(1.dp, NeonCyan),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "WRITE REVIEW: $appTitle",
                    color = NeonCyan,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Rating selector
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    (1..5).forEach { star ->
                        IconButton(onClick = { rating = star }) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = if (star <= rating) NeonAmber else TextMuted,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = authorName,
                    onValueChange = { authorName = it },
                    label = { Text("Your Operator Handle", color = TextMuted, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = CyberBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Review Comments & Feedback", color = TextMuted, fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = CyberBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = TextMuted)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (comment.isNotBlank()) {
                                onSubmit(authorName, rating, comment)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black)
                    ) {
                        Text("SUBMIT REVIEW", fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
