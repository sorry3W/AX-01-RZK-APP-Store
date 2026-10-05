package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.AppItem
import com.example.model.SandboxType
import com.example.ui.components.CyberPill
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sin

@Composable
fun AppSandboxModal(
    app: AppItem,
    onClose: () -> Unit
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, Color(app.accentColorHex), RoundedCornerShape(16.dp))
                .testTag("app_sandbox_modal"),
            color = CyberDarkBg
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header HUD
                SandboxHeader(app = app, onClose = onClose)

                // Sandbox Content Body
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    when (app.sandboxType) {
                        SandboxType.APEX_TERMINAL -> ApexTerminalSandbox()
                        SandboxType.NEURAL_STUDIO -> NeuralStudioSandbox()
                        SandboxType.QUANTUM_VPN -> QuantumVpnSandbox()
                        SandboxType.OVERCLOCK_OPTIMIZER -> OverclockOptimizerSandbox()
                        SandboxType.BIOSYNC_METRICS -> BioSyncMetricsSandbox()
                        SandboxType.CHRONO_RACER -> ChronoRacerSandbox()
                        SandboxType.HOLO_CANVAS -> HoloCanvasSandbox()
                        SandboxType.VORTEX_SYNTH -> VortexSynthSandbox()
                        SandboxType.CUSTOM_APP -> CustomAppSandbox(app = app)
                    }
                }
            }
        }
    }
}

@Composable
private fun SandboxHeader(
    app: AppItem,
    onClose: () -> Unit
) {
    Surface(
        color = CyberSurface,
        border = BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(NeonEmerald)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = app.title,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        CyberPill(text = "LIVE SANDBOX", color = Color(app.accentColorHex))
                    }
                    Text(
                        text = "Isolated AX-01 Quantum Container • v${app.version}",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(CyberCard)
                    .testTag("sandbox_close_btn")
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close Sandbox",
                    tint = TextPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

// 1. APEX TERMINAL OS SANDBOX
@Composable
fun ApexTerminalSandbox() {
    var commandInput by remember { mutableStateOf("") }
    val terminalLines = remember {
        mutableStateListOf(
            "AX-01 Apex Terminal Kernel v2.9.0-quantum initialized.",
            "Type 'help' to inspect available cyber shell commands.",
            "Security clearance: LEVEL-OMEGA. Data feed sync: ACTIVE.",
            "Ready for commands:"
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF030712)),
            border = BorderStroke(1.dp, CyberBorder),
            shape = RoundedCornerShape(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(terminalLines) { line ->
                    Text(
                        text = line,
                        color = when {
                            line.startsWith("guest@ax-01:") -> NeonCyan
                            line.startsWith("[!]") -> NeonAmber
                            line.startsWith("[ERR]") -> NeonRose
                            line.startsWith("[+]") -> NeonEmerald
                            else -> Color(0xFF86EFAC)
                        },
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Quick Command Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf("help", "scan", "sysinfo", "matrix", "ax-status", "clear").forEach { cmd ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(CyberCardElevated)
                        .border(1.dp, CyberBorder, RoundedCornerShape(4.dp))
                        .clickable { commandInput = cmd }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(cmd, color = NeonCyan, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Command Prompt Input
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = commandInput,
                onValueChange = { commandInput = it },
                modifier = Modifier
                    .weight(1f)
                    .testTag("terminal_input"),
                placeholder = {
                    Text("Enter command...", color = TextMuted, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = CyberBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary,
                    cursorColor = NeonCyan
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    val cmd = commandInput.trim().lowercase()
                    if (cmd.isNotEmpty()) {
                        terminalLines.add("guest@ax-01:~$ $commandInput")
                        when (cmd) {
                            "help" -> {
                                terminalLines.add("Commands: help, scan, sysinfo, matrix, ax-status, ping, quote, clear")
                            }
                            "scan" -> {
                                terminalLines.add("[+] Scanning AX-01 Quantum Nodes...")
                                terminalLines.add("[+] Found 4 Active Relays: Tokyo, Zurich, Reykjavik, Singapore")
                                terminalLines.add("[+] Zero vulnerabilities detected. 1000-year quantum seal intact.")
                            }
                            "sysinfo" -> {
                                terminalLines.add("HOST: AX-01RZK Softoware System")
                                terminalLines.add("ARCHITECT: Sultan Mahmud Sumon (rzk)")
                                terminalLines.add("KERNEL: Quantum Lattice v24.8.9")
                                terminalLines.add("SECURITY: AI Quantum 1000-Year Protocol")
                            }
                            "matrix" -> {
                                terminalLines.add("01000001 01011000 00101101 00110000 00110001")
                                terminalLines.add(">> NEURAL LINK ESTABLISHED TO MATRIX CORE <<")
                            }
                            "ax-status" -> {
                                terminalLines.add("[+] Automated Data Feed: Every 24 Hours")
                                terminalLines.add("[+] Support SMS: 01990761212")
                                terminalLines.add("[+] bKash/Nagad Merchant: 01640646714")
                            }
                            "ping" -> {
                                terminalLines.add("PING node.ax01rzk.com: time=2.4ms ttl=64")
                            }
                            "quote" -> {
                                terminalLines.add("\"শূণ্য থেকে শুরু শূন্য তেই শেষ\"")
                            }
                            "clear" -> {
                                terminalLines.clear()
                            }
                            else -> {
                                terminalLines.add("[ERR] Unknown command: '$cmd'. Type 'help' for manual.")
                            }
                        }
                        commandInput = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.height(52.dp).testTag("terminal_exec_btn")
            ) {
                Icon(Icons.Default.Send, contentDescription = "Run")
            }
        }
    }
}

// 2. NEURAL STUDIO AX SANDBOX
@Composable
fun NeuralStudioSandbox() {
    var prompt by remember { mutableStateOf("Synthesize a quantum security bypass report for AX-Shield enclave.") }
    var generatedText by remember { mutableStateOf("") }
    var isGenerating by remember { mutableStateOf(false) }
    var temperature by remember { mutableFloatStateOf(0.7f) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberCard),
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("NEURAL PROMPT MATRIX", color = NeonPurple, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = prompt,
                    onValueChange = { prompt = it },
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonPurple,
                        unfocusedBorderColor = CyberBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Creativity Temp: ${String.format("%.1f", temperature)}", color = TextSecondary, fontSize = 11.sp)
                    Slider(
                        value = temperature,
                        onValueChange = { temperature = it },
                        modifier = Modifier.width(160.dp),
                        colors = SliderDefaults.colors(thumbColor = NeonPurple, activeTrackColor = NeonPurple)
                    )
                }

                Button(
                    onClick = {
                        isGenerating = true
                        generatedText = ""
                        scope.launch {
                            val fullResponse = """
[NEURAL SYNTHESIS COMPLETE]
Model: AX-Quantized-NPU-v4
Latency: 18ms | Tokens: 428 | Security Clearance: Validated

Analysis: The target enclave operates under the 1,000-year quantum cryptographic cipher established by Sultan Mahmud Sumon (rzk). All brute-force attempts on lattice points yield mathematical collapse into zero-state: "শূণ্য থেকে শুরু শূন্য তেই শেষ".
Recommendation: Maintain automated 24-hour feed updates and verify Mastercard transaction tokens (5191555506390948).
                            """.trimIndent()

                            for (char in fullResponse) {
                                generatedText += char
                                delay(6)
                            }
                            isGenerating = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple, contentColor = Color.White),
                    modifier = Modifier.fillMaxWidth().testTag("generate_neural_btn"),
                    enabled = !isGenerating
                ) {
                    if (isGenerating) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White, strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("SYNTHESIZING ON NPU...", fontSize = 12.sp)
                    } else {
                        Text("RUN NEURAL INFERENCE", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF070B18)),
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                item {
                    Text(
                        text = if (generatedText.isEmpty()) "Awaiting prompt synthesis command..." else generatedText,
                        color = if (generatedText.isEmpty()) TextMuted else TextPrimary,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

// 3. AX-SHIELD QUANTUM VPN SANDBOX
@Composable
fun QuantumVpnSandbox() {
    var isConnected by remember { mutableStateOf(true) }
    var selectedNode by remember { mutableStateOf("Neo-Tokyo [Relay #01]") }
    var keyCountdown by remember { mutableIntStateOf(15) }
    var encryptedPackets by remember { mutableIntStateOf(48920) }

    LaunchedEffect(isConnected) {
        while (isConnected) {
            delay(1000)
            keyCountdown = if (keyCountdown <= 1) 15 else keyCountdown - 1
            encryptedPackets += (12..68).random()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberCard),
            border = BorderStroke(1.dp, if (isConnected) NeonEmerald else CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(if (isConnected) NeonEmerald.copy(alpha = 0.2f) else CyberSurface)
                        .border(2.dp, if (isConnected) NeonEmerald else CyberBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Security,
                        contentDescription = "Shield",
                        tint = if (isConnected) NeonEmerald else TextMuted,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (isConnected) "QUANTUM TUNNEL ACTIVE" else "TUNNEL DISCONNECTED",
                    color = if (isConnected) NeonEmerald else TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Lattice Cryptography: 1,000-Year Zero Trust",
                    color = TextMuted,
                    fontSize = 11.sp
                )

                Spacer(modifier = Modifier.height(14.dp))
                Switch(
                    checked = isConnected,
                    onCheckedChange = { isConnected = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = NeonEmerald,
                        checkedTrackColor = NeonEmerald.copy(alpha = 0.4f)
                    ),
                    modifier = Modifier.testTag("vpn_toggle_switch")
                )
            }
        }

        // Live Telemetry
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
                    Text("Dynamic Key Rotation", color = TextSecondary, fontSize = 12.sp)
                    Text("Next in ${keyCountdown}s", color = NeonCyan, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Encrypted Packets", color = TextSecondary, fontSize = 12.sp)
                    Text("$encryptedPackets PKTS", color = NeonEmerald, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Ping Latency", color = TextSecondary, fontSize = 12.sp)
                    Text("4.2 ms (Zero Jitter)", color = NeonAmber, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
                }
            }
        }

        // Node Selectors
        Text("RELAY NODES", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Neo-Tokyo", "Zurich", "Reykjavik", "Singapore").forEach { city ->
                val isSel = selectedNode.contains(city)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSel) NeonCyan.copy(alpha = 0.2f) else CyberCard)
                        .border(1.dp, if (isSel) NeonCyan else CyberBorder, RoundedCornerShape(6.dp))
                        .clickable { selectedNode = "$city [Relay Active]" }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(city, color = if (isSel) NeonCyan else TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// 4. OVERCLOCK OPTIMIZER AX
@Composable
fun OverclockOptimizerSandbox() {
    var clockGhz by remember { mutableFloatStateOf(3.2f) }
    var tempC by remember { mutableIntStateOf(42) }
    var isCryoActive by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberCard),
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${String.format("%.2f", clockGhz)} GHz",
                    color = if (clockGhz > 3.6f) NeonRose else NeonCyan,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = "TARGET CLUSTER FREQUENCY",
                    color = TextMuted,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(14.dp))
                Slider(
                    value = clockGhz,
                    onValueChange = {
                        clockGhz = it
                        tempC = (38 + (it - 2.0f) * 18).toInt().coerceAtMost(88)
                    },
                    valueRange = 2.0f..4.4f,
                    colors = SliderDefaults.colors(
                        thumbColor = NeonCyan,
                        activeTrackColor = NeonCyan
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Eco 2.0 GHz", color = TextMuted, fontSize = 10.sp)
                    Text("Turbo 4.4 GHz", color = NeonRose, fontSize = 10.sp)
                }
            }
        }

        // Thermals & Cryo
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
                    Column {
                        Text("Core Temperature", color = TextSecondary, fontSize = 12.sp)
                        Text("$tempC °C", color = if (tempC > 65) NeonRose else NeonEmerald, fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    }

                    Button(
                        onClick = {
                            isCryoActive = true
                            tempC = (tempC - 18).coerceAtLeast(28)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCryoActive) NeonCyan else NeonCyan.copy(alpha = 0.2f),
                            contentColor = if (isCryoActive) Color.Black else NeonCyan
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("cryo_flush_btn")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("CRYO PURGE", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// 5. BIOSYNC CYBERMETRICS (Live ECG Canvas)
@Composable
fun BioSyncMetricsSandbox() {
    var heartRate by remember { mutableIntStateOf(76) }
    var stressIndex by remember { mutableIntStateOf(24) }

    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // ECG Oscilloscope
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF030A14)),
            border = BorderStroke(1.dp, NeonRose.copy(alpha = 0.6f)),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                    val width = size.width
                    val height = size.height
                    val midY = height / 2

                    // Draw grid lines
                    for (x in 0..width.toInt() step 40) {
                        drawLine(
                            color = Color(0x15FF1744),
                            start = Offset(x.toFloat(), 0f),
                            end = Offset(x.toFloat(), height),
                            strokeWidth = 1f
                        )
                    }
                    for (y in 0..height.toInt() step 40) {
                        drawLine(
                            color = Color(0x15FF1744),
                            start = Offset(0f, y.toFloat()),
                            end = Offset(width, y.toFloat()),
                            strokeWidth = 1f
                        )
                    }

                    // Draw ECG waveform
                    val path = Path()
                    var started = false
                    for (x in 0..width.toInt() step 4) {
                        val normX = x / width
                        val pulse = sin(normX * 18 + phase)
                        val ecgSpike = if ((x.toInt() % 160) in 60..80) {
                            -height * 0.35f * sin((x % 160 - 60) * 0.15f)
                        } else {
                            pulse * 12f
                        }
                        val y = midY + ecgSpike

                        if (!started) {
                            path.moveTo(x.toFloat(), y)
                            started = true
                        } else {
                            path.lineTo(x.toFloat(), y)
                        }
                    }

                    drawPath(
                        path = path,
                        color = NeonRose,
                        style = Stroke(width = 2.5f, cap = StrokeCap.Round)
                    )
                }

                // HUD overlay
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("ECG LEAD II • ENCLAVE ACTIVE", color = NeonRose, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                    Text("$heartRate BPM", color = NeonRose, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                }
            }
        }

        // Biometrics stats
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberCardElevated),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Autonomic Stress", color = TextSecondary, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("$stressIndex / 100", color = NeonEmerald, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Text("State: Optimal Enclave", color = TextMuted, fontSize = 9.sp)
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = CyberCardElevated),
                border = BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.weight(1f)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Oxygen Saturation", color = TextSecondary, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("99.4 %", color = NeonCyan, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                    Text("Neural Sync: 100%", color = TextMuted, fontSize = 9.sp)
                }
            }
        }
    }
}

// 6. CHRONO RACER 2099 SANDBOX
@Composable
fun ChronoRacerSandbox() {
    var speedKmh by remember { mutableIntStateOf(210) }
    var nitroFuel by remember { mutableFloatStateOf(1.0f) }
    var isNitroActive by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            border = BorderStroke(2.dp, if (isNitroActive) NeonCyan else NeonAmber),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$speedKmh",
                    color = if (isNitroActive) NeonCyan else NeonAmber,
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace
                )
                Text("KM/H • ANTIGRAV VELOCITY", color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)

                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Photon Nitro Tank", color = TextSecondary, fontSize = 11.sp)
                    Text("${(nitroFuel * 100).toInt()}%", color = NeonCyan, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(CyberSurface)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(nitroFuel)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Brush.horizontalGradient(listOf(NeonCyan, NeonPurple)))
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    speedKmh = (speedKmh + 25).coerceAtMost(480)
                },
                colors = ButtonDefaults.buttonColors(containerColor = CyberCardElevated, contentColor = TextPrimary),
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text("ACCEL +", fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = {
                    speedKmh = (speedKmh - 30).coerceAtLeast(0)
                },
                colors = ButtonDefaults.buttonColors(containerColor = CyberCardElevated, contentColor = TextPrimary),
                modifier = Modifier.weight(1f).height(50.dp)
            ) {
                Text("BRAKE -", fontWeight = FontWeight.Bold)
            }
        }

        Button(
            onClick = {
                if (nitroFuel > 0.2f && !isNitroActive) {
                    isNitroActive = true
                    scope.launch {
                        speedKmh += 150
                        nitroFuel = (nitroFuel - 0.35f).coerceAtLeast(0f)
                        delay(2000)
                        speedKmh -= 120
                        isNitroActive = false
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isNitroActive) NeonCyan else NeonAmber,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("nitro_boost_btn")
        ) {
            Icon(Icons.Default.ElectricBolt, contentDescription = null)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (isNitroActive) "NITRO WARP ENGAGED!" else "ENGAGE PHOTON NITRO BOOST",
                fontWeight = FontWeight.Black,
                fontSize = 13.sp
            )
        }
    }
}

// 7. HOLO CANVAS 3D SANDBOX
@Composable
fun HoloCanvasSandbox() {
    val points = remember { mutableStateListOf<Offset>() }
    var selectedColor by remember { mutableStateOf(NeonCyan) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(NeonCyan, NeonPurple, NeonEmerald, NeonAmber).forEach { col ->
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .clip(CircleShape)
                            .background(col)
                            .border(
                                2.dp,
                                if (selectedColor == col) Color.White else Color.Transparent,
                                CircleShape
                            )
                            .clickable { selectedColor = col }
                    )
                }
            }

            OutlinedButton(
                onClick = { points.clear() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonRose),
                border = BorderStroke(1.dp, NeonRose),
                modifier = Modifier.height(32.dp)
            ) {
                Text("CLEAR", fontSize = 10.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF030712))
                .border(1.dp, CyberBorder, RoundedCornerShape(10.dp))
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        change.consume()
                        points.add(change.position)
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                for (pt in points) {
                    drawCircle(
                        color = selectedColor.copy(alpha = 0.8f),
                        radius = 6f,
                        center = pt
                    )
                    drawCircle(
                        color = selectedColor.copy(alpha = 0.3f),
                        radius = 14f,
                        center = pt
                    )
                }
            }

            if (points.isEmpty()) {
                Text(
                    text = "Touch and drag to emit quantum holographic particles...",
                    color = TextMuted,
                    fontSize = 11.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

// 8. VORTEX SYNTHWAVE STUDIO SANDBOX
@Composable
fun VortexSynthSandbox() {
    var activePadIndex by remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()

    val padNames = listOf(
        "KICK 909", "SNARE GATED", "NEON ARP", "SUB BASS",
        "CYBER LEAD", "HI-HAT 808", "LASER DROP", "VOX CHORD"
    )
    val padColors = listOf(
        NeonCyan, NeonPurple, NeonEmerald, NeonAmber,
        NeonRose, NeonCyan, NeonPurple, NeonEmerald
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text("8-PAD CYBER DRUM & SYNTH MATRIX", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(padNames.size) { idx ->
                val padColor = padColors[idx]
                val isActive = activePadIndex == idx

                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isActive) padColor else CyberCardElevated)
                        .border(1.dp, padColor.copy(alpha = 0.7f), RoundedCornerShape(10.dp))
                        .clickable {
                            activePadIndex = idx
                            scope.launch {
                                delay(250)
                                if (activePadIndex == idx) activePadIndex = null
                            }
                        }
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = padNames[idx],
                            color = if (isActive) Color.Black else TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = if (isActive) "PLAYING" else "PAD #${idx + 1}",
                            color = if (isActive) Color.Black.copy(alpha = 0.7f) else TextMuted,
                            fontSize = 9.sp
                        )
                    }
                }
            }
        }
    }
}

// 9. CUSTOM APP SANDBOX (For user-created apps in Creator Studio!)
@Composable
fun CustomAppSandbox(app: AppItem) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberCard),
            border = BorderStroke(1.dp, Color(app.accentColorHex)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(app.accentColorHex).copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            app.title.take(2).uppercase(),
                            color = Color(app.accentColorHex),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(app.title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(app.tagline, color = TextSecondary, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(app.description, color = TextPrimary, fontSize = 12.sp, lineHeight = 17.sp)

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Developer: ${app.developer}", color = TextMuted, fontSize = 11.sp)
                    Text("Size: ${app.sizeMb} MB", color = TextMuted, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                }
            }
        }

        // Simulated Interactive Runtime
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF030712)),
            border = BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(14.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color(app.accentColorHex),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Custom AX-01 Runtime Active",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "All quantum permissions and sandbox enclaves granted.",
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
        }
    }
}
