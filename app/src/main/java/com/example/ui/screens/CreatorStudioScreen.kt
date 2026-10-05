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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppItem
import com.example.model.Category
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
fun CreatorStudioScreen(
    customApps: List<AppItem>,
    onCreateApp: (
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
    ) -> Unit,
    onDeleteCustomApp: (String) -> Unit,
    onOpenSandbox: (AppItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("HyperDrive Quantum") }
    var tagline by remember { mutableStateOf("Sub-light warp telemetry engine") }
    var description by remember { mutableStateOf("Next-gen quantum drive governor with real-time warp bubble frequency stabilization and automated data feed.") }
    var selectedCategory by remember { mutableStateOf(Category.CYBER_TOOLS) }
    var packageId by remember { mutableStateOf("com.cyber.hyperdrive") }
    var version by remember { mutableStateOf("1.0.0") }
    var selectedColorHex by remember { mutableStateOf(0xFF00E5FFL) }
    var selectedTemplate by remember { mutableStateOf("APEX_TERMINAL") }
    var enableNpu by remember { mutableStateOf(true) }
    var enableQuantumCipher by remember { mutableStateOf(true) }
    var deploymentSuccessMsg by remember { mutableStateOf<String?>(null) }

    val colorOptions = listOf(
        0xFF00E5FFL to "Neon Cyan",
        0xFFD500F9L to "Neon Magenta",
        0xFF00E676L to "Neon Emerald",
        0xFFFFB300L to "Neon Gold",
        0xFFFF1744L to "Neon Crimson"
    )

    val templateOptions = listOf(
        "APEX_TERMINAL" to "Cyber Shell CLI",
        "NEURAL_STUDIO" to "Neural AI Generator",
        "QUANTUM_VPN" to "Quantum Lattice VPN",
        "OVERCLOCK_OPTIMIZER" to "Cluster Overclocker",
        "CHRONO_RACER" to "Antigrav Racer HUD",
        "VORTEX_SYNTH" to "8-Pad Synthwave Studio"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberCardElevated),
                border = BorderStroke(1.dp, NeonPurple.copy(alpha = 0.8f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Build, contentDescription = null, tint = NeonPurple, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "AX-01 APP STORE CREATOR STUDIO",
                            color = NeonPurple,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        "Design, configure, and compile custom cyber modules directly into the AX-01 App Store Matrix. Includes instant sandbox simulation.",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Section 1: App Identity
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberCard),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("1. MODULE IDENTITY", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("App Module Title", fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth().testTag("creator_title_input"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = CyberBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = tagline,
                        onValueChange = { tagline = it },
                        label = { Text("Short Tagline", fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth().testTag("creator_tagline_input"),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = CyberBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Detailed Specification", fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth().height(84.dp).testTag("creator_desc_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = CyberBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                }
            }
        }

        // Section 2: Technical & Category
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberCard),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("2. CATEGORY & PACKAGE SPEC", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(Category.NEURAL_AI, Category.CYBER_TOOLS, Category.QUANTUM_SECURITY, Category.HOLO_GAMES).forEach { cat ->
                            val isSel = selectedCategory == cat
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSel) NeonCyan.copy(alpha = 0.25f) else CyberSurface)
                                    .border(1.dp, if (isSel) NeonCyan else CyberBorder, RoundedCornerShape(6.dp))
                                    .clickable { selectedCategory = cat }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(cat.displayName, color = if (isSel) NeonCyan else TextSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = packageId,
                            onValueChange = { packageId = it },
                            label = { Text("Package ID", fontSize = 10.sp) },
                            modifier = Modifier.weight(2f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonCyan,
                                unfocusedBorderColor = CyberBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )

                        OutlinedTextField(
                            value = version,
                            onValueChange = { version = it },
                            label = { Text("Version", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonCyan,
                                unfocusedBorderColor = CyberBorder,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )
                    }
                }
            }
        }

        // Section 3: Visual Accent Color & Sandbox Logic Template
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberCard),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("3. NEON THEME & SANDBOX RUNTIME", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(10.dp))

                    // Color choice
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        colorOptions.forEach { (colHex, name) ->
                            val col = Color(colHex)
                            val isSel = selectedColorHex == colHex
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(col)
                                    .border(2.dp, if (isSel) Color.White else Color.Transparent, CircleShape)
                                    .clickable { selectedColorHex = colHex }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text("INTERACTIVE SANDBOX ENGINE", color = TextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(6.dp))

                    templateOptions.forEach { (templateKey, templateName) ->
                        val isSel = selectedTemplate == templateKey
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSel) CyberCardElevated else CyberSurface)
                                .border(1.dp, if (isSel) NeonCyan else CyberBorder, RoundedCornerShape(6.dp))
                                .clickable { selectedTemplate = templateKey }
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(templateName, color = if (isSel) NeonCyan else TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            if (isSel) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }
        }

        // Section 4: Quantum Permissions
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberCard),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("4. QUANTUM ENCLAVE PERMISSIONS", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("On-Device Neural NPU Inference", color = TextPrimary, fontSize = 12.sp)
                        Switch(
                            checked = enableNpu,
                            onCheckedChange = { enableNpu = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = NeonCyan)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("1000-Year Quantum Security Cipher", color = TextPrimary, fontSize = 12.sp)
                        Switch(
                            checked = enableQuantumCipher,
                            onCheckedChange = { enableQuantumCipher = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = NeonEmerald)
                        )
                    }
                }
            }
        }

        // Deploy Button
        item {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onCreateApp(
                            title.trim(),
                            tagline.trim(),
                            description.trim(),
                            selectedCategory,
                            packageId.trim(),
                            version.trim(),
                            selectedColorHex,
                            "Build",
                            selectedTemplate
                        ) { newId ->
                            deploymentSuccessMsg = "Module '$title' successfully deployed to AX-01 Matrix!"
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonPurple, contentColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("deploy_custom_app_btn")
            ) {
                Icon(Icons.Default.RocketLaunch, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("COMPILE & DEPLOY TO AX-01 MATRIX", fontWeight = FontWeight.Black, fontSize = 12.sp)
            }
        }

        // Deployment status feedback
        if (deploymentSuccessMsg != null) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF003622)),
                    border = BorderStroke(1.dp, NeonEmerald),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = NeonEmerald)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(deploymentSuccessMsg!!, color = NeonEmerald, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Custom Created Apps List
        if (customApps.isNotEmpty()) {
            item {
                Text(
                    text = "YOUR CREATED MODULES (${customApps.size})",
                    color = TextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            items(customApps, key = { it.id }) { cApp ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberCardElevated),
                    border = BorderStroke(1.dp, Color(cApp.accentColorHex)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(cApp.title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                CyberPill(text = "LIVE", color = NeonEmerald)
                            }
                            Text(cApp.tagline, color = TextSecondary, fontSize = 11.sp)
                            Text("ID: ${cApp.id} • v${cApp.version}", color = TextMuted, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = { onOpenSandbox(cApp) },
                                colors = ButtonDefaults.buttonColors(containerColor = NeonEmerald, contentColor = Color.Black),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text("RUN", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            IconButton(onClick = { onDeleteCustomApp(cApp.id) }, modifier = Modifier.size(32.dp)) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = NeonRose, modifier = Modifier.size(16.dp))
                            }
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
