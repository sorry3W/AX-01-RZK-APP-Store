package com.example.data

import com.example.model.AppItem
import com.example.model.Category
import com.example.model.SandboxType

object SampleAppData {
    val flagshipApps: List<AppItem> = listOf(
        AppItem(
            id = "ax_neural_studio",
            title = "NeuralStudio AX",
            tagline = "On-Device Neural Engine & Synthesis Matrix",
            description = "NeuralStudio AX harnesses state-of-the-art quantized generative neural networks. Generate synthetic data, code snippets, cyberpunk lore, and vector prompts in milliseconds with zero cloud latency.",
            developer = "AX-01 RZK Cyber Labs",
            category = Category.NEURAL_AI,
            rating = 4.9f,
            reviewCount = 14200,
            downloadCount = "2.8M",
            sizeMb = 48.5,
            version = "3.4.0",
            iconVectorName = "Psychology",
            accentColorHex = 0xFFD500F9,
            sandboxType = SandboxType.NEURAL_STUDIO,
            isFeatured = true,
            isAppOfTheDay = true,
            rank = 1,
            features = listOf(
                "On-Device Quantized Neural Engine",
                "Instant Text & Matrix Synthesis",
                "Hardware NPU Acceleration",
                "Zero Cloud Footprint & Safe Mode"
            )
        ),
        AppItem(
            id = "ax_quantum_vpn",
            title = "AX-Shield Quantum VPN",
            tagline = "1,000-Year Quantum-Resistant Encryption Tunnel",
            description = "Military-grade quantum lattice cryptography designed by AX-01 RZK Software Company. Dynamic 15-second key rotations, multi-hop anonymous routing through Neo-Tokyo, Zurich, and Singapore.",
            developer = "AX-01 RZK Software Company",
            category = Category.QUANTUM_SECURITY,
            rating = 4.95f,
            reviewCount = 28900,
            downloadCount = "5.1M",
            sizeMb = 24.2,
            version = "4.1.2",
            iconVectorName = "Security",
            accentColorHex = 0xFF00E676,
            sandboxType = SandboxType.QUANTUM_VPN,
            isFeatured = true,
            rank = 2,
            features = listOf(
                "Post-Quantum Lattice Cryptography",
                "15-Second Dynamic Key Rotation",
                "Zero-Log RAM-Only Routing",
                "Global Cyber Nodes (Tokyo, Zurich, Singapore)"
            )
        ),
        AppItem(
            id = "ax_apex_terminal",
            title = "Apex Terminal OS",
            tagline = "Hacker CLI, System Matrix & Enclave Shell",
            description = "The ultimate terminal emulator for cyber operators. Supports custom command scripts, system inspection, real-time matrix rains, cryptographic hashing, and automated diagnostic feeds.",
            developer = "Sumon Mahmud (rzk)",
            category = Category.CYBER_TOOLS,
            rating = 4.88f,
            reviewCount = 19400,
            downloadCount = "3.4M",
            sizeMb = 18.6,
            version = "2.9.0",
            iconVectorName = "Terminal",
            accentColorHex = 0xFF00E5FF,
            sandboxType = SandboxType.APEX_TERMINAL,
            isFeatured = true,
            rank = 3,
            features = listOf(
                "Bash/Zsh Compatible Cyber Shell",
                "Integrated Matrix Digital Rain Generator",
                "Real-time System Enclave Diagnostics",
                "Direct Node Ping & Network Trace"
            )
        ),
        AppItem(
            id = "ax_chrono_racer",
            title = "ChronoRacer 2099",
            tagline = "Antigravity Neon Racing with Photon Nitro",
            description = "High-velocity antigravity racer in neon cyberpunk megacity skylines. Feel the adrenaline of photon boost turbines, cyber soundtrack, and precision tachometer instrumentation.",
            developer = "NeonForge Interactive",
            category = Category.HOLO_GAMES,
            rating = 4.85f,
            reviewCount = 41200,
            downloadCount = "8.2M",
            sizeMb = 82.4,
            version = "1.8.4",
            iconVectorName = "Speed",
            accentColorHex = 0xFFFFB300,
            sandboxType = SandboxType.CHRONO_RACER,
            isFeatured = true,
            rank = 4,
            features = listOf(
                "Photon Nitro Boost Propulsion",
                "Real-time Antigrav Cockpit HUD",
                "60 FPS Neo-Tokyo Circuit Simulator",
                "Dynamic Haptic Engine Rumble"
            )
        ),
        AppItem(
            id = "ax_biosync_metrics",
            title = "BioSync CyberMetrics",
            tagline = "Biometric Telemetry & Real-Time ECG Oscilloscope",
            description = "Direct neural link health monitor. Visualizes live cardiac rhythm waveforms, calculates autonomic stress index, and verifies user biological telemetry in the AX-01 enclave.",
            developer = "AX-01 RZK BioTech",
            category = Category.BIOMETRICS,
            rating = 4.92f,
            reviewCount = 11300,
            downloadCount = "1.9M",
            sizeMb = 29.1,
            version = "3.0.1",
            iconVectorName = "MonitorHeart",
            accentColorHex = 0xFFFF1744,
            sandboxType = SandboxType.BIOSYNC_METRICS,
            rank = 5,
            features = listOf(
                "Real-Time Oscilloscope Waveform Canvas",
                "Autonomic Neural Stress Index",
                "Continuous Heart Rate BPM Tracker",
                "Biometric Enclave Vault Export"
            )
        ),
        AppItem(
            id = "ax_overclock_optimizer",
            title = "Overclock Optimizer AX",
            tagline = "SoC Frequency Booster & Enclave Cooler",
            description = "Squeeze maximum compute performance from your chipset. Dial up GPU frequencies, activate cryo cooling mode, and purge system cache with a single tap.",
            developer = "AX-01 Core Architecture",
            category = Category.CYBER_TOOLS,
            rating = 4.79f,
            reviewCount = 16800,
            downloadCount = "4.2M",
            sizeMb = 15.2,
            version = "5.0.0",
            iconVectorName = "Tune",
            accentColorHex = 0xFF00E5FF,
            sandboxType = SandboxType.OVERCLOCK_OPTIMIZER,
            rank = 6,
            features = listOf(
                "Real-Time Clock Frequency Tachometer",
                "One-Tap Memory Enclave Flush",
                "Cryo Cooler Simulation Mode",
                "Battery Anode Health Sensor"
            )
        ),
        AppItem(
            id = "ax_holo_canvas",
            title = "HoloCanvas 3D",
            tagline = "Interactive Spatial Neon Particle Emitter",
            description = "Turn your display into a reactive holographic surface. Touch to spawn glowing energy particles, modulate quantum electromagnetic fields, and sketch with luminescent cyber ink.",
            developer = "HoloMatrix Studios",
            category = Category.HOLO_GAMES,
            rating = 4.82f,
            reviewCount = 9400,
            downloadCount = "1.5M",
            sizeMb = 34.0,
            version = "2.1.0",
            iconVectorName = "Draw",
            accentColorHex = 0xFFD500F9,
            sandboxType = SandboxType.HOLO_CANVAS,
            rank = 7,
            features = listOf(
                "Multi-Touch Luminescent Particle Physics",
                "Magnetic Flux Vortex Simulation",
                "Color Shift Wave Synthesis",
                "Export Cyber Art Vectors"
            )
        ),
        AppItem(
            id = "ax_vortex_synth",
            title = "Vortex Synthwave Studio",
            tagline = "Cyberpunk 8-Pad Synth & Drum Sequencer",
            description = "Craft retro-futuristic synthwave tracks on an interactive neon MPC pad grid. Features arpeggiated basslines, gated snares, neon visualizer feedback, and real-time audio playback.",
            developer = "Vortex Sound Labs",
            category = Category.AUDIO_MEDIA,
            rating = 4.87f,
            reviewCount = 13500,
            downloadCount = "2.3M",
            sizeMb = 41.8,
            version = "3.2.0",
            iconVectorName = "GraphicEq",
            accentColorHex = 0xFFFF4081,
            sandboxType = SandboxType.VORTEX_SYNTH,
            rank = 8,
            features = listOf(
                "8-Pad Responsive Beat Matrix",
                "Real-Time Frequency Visualizer",
                "Preset Synthwave Loops & Bass Drops",
                "Studio Mix Enclave Recorder"
            )
        )
    )
}
