package com.example.model

enum class SandboxType {
    APEX_TERMINAL,
    NEURAL_STUDIO,
    QUANTUM_VPN,
    OVERCLOCK_OPTIMIZER,
    BIOSYNC_METRICS,
    CHRONO_RACER,
    HOLO_CANVAS,
    VORTEX_SYNTH,
    CUSTOM_APP
}

data class AppItem(
    val id: String,
    val title: String,
    val tagline: String,
    val description: String,
    val developer: String,
    val category: Category,
    val rating: Float,
    val reviewCount: Int,
    val downloadCount: String,
    val sizeMb: Double,
    val version: String,
    val iconVectorName: String,
    val accentColorHex: Long,
    val sandboxType: SandboxType,
    val isFeatured: Boolean = false,
    val isAppOfTheDay: Boolean = false,
    val rank: Int = 0,
    val features: List<String> = emptyList(),
    val changelog: String = "Automated data feed update v${version} with quantum stability patches.",
    val isCustomCreated: Boolean = false,
    val customCreatorConfig: String? = null
)
