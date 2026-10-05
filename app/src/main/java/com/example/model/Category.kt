package com.example.model

enum class Category(val displayName: String, val badgeColorHex: Long) {
    ALL("All Modules", 0xFF00E5FF),
    NEURAL_AI("Neural & AI", 0xFFD500F9),
    QUANTUM_SECURITY("Quantum Sec", 0xFF00E676),
    CYBER_TOOLS("Cyber Tools", 0xFF00E5FF),
    HOLO_GAMES("Holo Games", 0xFFFFB300),
    AUDIO_MEDIA("Audio & Synth", 0xFFFF4081),
    BIOMETRICS("Biometrics", 0xFF00E5FF),
    CREATOR_STUDIO("Creator Built", 0xFF7C4DFF);

    companion object {
        fun fromString(name: String?): Category {
            return entries.firstOrNull { it.name.equals(name, ignoreCase = true) } ?: ALL
        }
    }
}
