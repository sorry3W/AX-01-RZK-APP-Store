package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LegalContent
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
fun AttributionLegalScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Attribution & Lead", "Privacy Policy", "EULA & Licenses", "Terms of Service")

    fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Could not open URL", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendSms(phone: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phone"))
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Could not launch SMS app", Toast.LENGTH_SHORT).show()
        }
    }

    fun sendEmail(email: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Could not launch Email app", Toast.LENGTH_SHORT).show()
        }
    }

    fun copyToClipboard(label: String, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied $label", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    ) {
        // Tab Pills
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tabs.forEachIndexed { index, tabName ->
                val isSel = selectedTab == index
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSel) NeonCyan.copy(alpha = 0.25f) else CyberCard)
                        .border(1.dp, if (isSel) NeonCyan else CyberBorder, RoundedCornerShape(8.dp))
                        .clickable { selectedTab = index }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .testTag("legal_tab_$index")
                ) {
                    Text(
                        text = tabName,
                        color = if (isSel) NeonCyan else TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            when (selectedTab) {
                0 -> {
                    // Lead Architect & Attribution
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CyberCardElevated),
                            border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.8f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(54.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Brush.linearGradient(listOf(NeonCyan, NeonPurple))
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.Black, modifier = Modifier.size(32.dp))
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column {
                                        Text(
                                            text = LegalContent.LEAD_DEVELOPER,
                                            color = TextPrimary,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 18.sp
                                        )
                                        Text(
                                            text = LegalContent.ARCHITECT_TITLE,
                                            color = NeonCyan,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = LegalContent.COMPANY_NAME,
                                            color = TextMuted,
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // Quotation Banner
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF0F172A))
                                        .border(1.dp, NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                        .padding(12.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = LegalContent.SLOGAN,
                                            color = NeonCyan,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "Attribution & Support Core Creed",
                                            color = TextMuted,
                                            fontSize = 10.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Direct Support Contacts
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CyberCard),
                            border = BorderStroke(1.dp, CyberBorder),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text("DIRECT DEVELOPER SUPPORT & CHANNELS", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                Spacer(modifier = Modifier.height(10.dp))

                                SupportContactRow(
                                    icon = Icons.Default.Sms,
                                    label = "DIRECT SMS",
                                    value = LegalContent.DIRECT_SMS,
                                    actionLabel = "SEND SMS",
                                    onAction = { sendSms(LegalContent.DIRECT_SMS) },
                                    onCopy = { copyToClipboard("SMS Number", LegalContent.DIRECT_SMS) }
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                SupportContactRow(
                                    icon = Icons.Default.Email,
                                    label = "OFFICIAL EMAIL",
                                    value = LegalContent.EMAIL,
                                    actionLabel = "EMAIL",
                                    onAction = { sendEmail(LegalContent.EMAIL) },
                                    onCopy = { copyToClipboard("Email", LegalContent.EMAIL) }
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                SupportContactRow(
                                    icon = Icons.Default.Language,
                                    label = "WEBSITE",
                                    value = LegalContent.WEBSITE,
                                    actionLabel = "OPEN",
                                    onAction = { openUrl(LegalContent.WEBSITE) },
                                    onCopy = { copyToClipboard("Website", LegalContent.WEBSITE) }
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                SupportContactRow(
                                    icon = Icons.Default.Subscriptions,
                                    label = "YOUTUBE CHANNEL",
                                    value = "@ax-01rzk-youtube",
                                    actionLabel = "WATCH",
                                    onAction = { openUrl(LegalContent.YOUTUBE_CHANNEL) },
                                    onCopy = { copyToClipboard("YouTube", LegalContent.YOUTUBE_CHANNEL) }
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                SupportContactRow(
                                    icon = Icons.Default.OpenInBrowser,
                                    label = "FACEBOOK COMMUNITY",
                                    value = "AX-01 RZK Community Page",
                                    actionLabel = "VISIT",
                                    onAction = { openUrl(LegalContent.FACEBOOK_PAGE) },
                                    onCopy = { copyToClipboard("Facebook", LegalContent.FACEBOOK_PAGE) }
                                )
                            }
                        }
                    }

                    // Security & Quantum Disclaimers
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF030D1A)),
                            border = BorderStroke(1.dp, NeonEmerald),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text("AUTOMATED QUANTUM ARCHITECTURE", color = NeonEmerald, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(LegalContent.QUANTUM_PROMISE, color = TextPrimary, fontSize = 12.sp, lineHeight = 17.sp)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(LegalContent.COPYRIGHT, color = TextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(LegalContent.CONFIDENTIALITY_NOTICE, color = TextSecondary, fontSize = 10.sp, lineHeight = 14.sp)
                            }
                        }
                    }
                }

                1 -> {
                    // Privacy Policy
                    item {
                        LegalTextCard(
                            title = "PRIVACY POLICY FOR AX-01 RZK SOFTWARE COMPANY",
                            content = LegalContent.PRIVACY_POLICY
                        )
                    }
                }

                2 -> {
                    // EULA
                    item {
                        LegalTextCard(
                            title = "SOFTWARE LICENSES & END-USER LICENSE AGREEMENT (EULA)",
                            content = LegalContent.EULA
                        )
                    }
                }

                3 -> {
                    // Terms of Service
                    item {
                        LegalTextCard(
                            title = "TERMS OF SERVICE FOR AX-01 RZK SOFTWARE COMPANY",
                            content = LegalContent.TERMS_OF_SERVICE
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun SupportContactRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    actionLabel: String,
    onAction: () -> Unit,
    onCopy: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(CyberSurface)
            .border(1.dp, CyberBorder, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(label, color = TextMuted, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                Text(value, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onCopy, modifier = Modifier.size(28.dp)) {
                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = TextMuted, modifier = Modifier.size(14.dp))
            }
            Spacer(modifier = Modifier.width(4.dp))
            OutlinedButton(
                onClick = onAction,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonCyan),
                border = BorderStroke(1.dp, NeonCyan),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.height(28.dp)
            ) {
                Text(actionLabel, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun LegalTextCard(
    title: String,
    content: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberCard),
        border = BorderStroke(1.dp, CyberBorder),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                color = NeonCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = content,
                color = TextPrimary,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}
