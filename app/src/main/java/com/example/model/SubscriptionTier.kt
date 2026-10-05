package com.example.model

enum class SubscriptionTier(
    val tierId: String,
    val title: String,
    val priceBdtFormatted: String,
    val priceNumeric: Long,
    val durationText: String,
    val description: String,
    val perks: List<String>,
    val badgeColorHex: Long,
    val isPopular: Boolean = false
) {
    TRIAL_60_DAYS(
        tierId = "tier_trial",
        title = "Trial Sandbox",
        priceBdtFormatted = "BDT 0",
        priceNumeric = 0L,
        durationText = "60 Days Full Access",
        description = "Initial developer evaluation with complete AX-01 Quantum access for 60 days.",
        perks = listOf(
            "Access to All 8 Core Sandboxes",
            "Store App Downloads & Cache Enclave",
            "Automated 24h Data Feed Sync",
            "60-Day Evaluation License"
        ),
        badgeColorHex = 0xFF94A3B8
    ),
    MONTHLY_PLAN(
        tierId = "tier_monthly",
        title = "Monthly Plan",
        priceBdtFormatted = "BDT 1,000",
        priceNumeric = 1000L,
        durationText = "Per Month",
        description = "Standard recurring license for cyber app installation, testing, and sandbox simulations.",
        perks = listOf(
            "Unlimited Store Downloads",
            "AX-Shield Zero-Trust Security",
            "App Store Creator Module (10 Apps)",
            "Priority Node Routing"
        ),
        badgeColorHex = 0xFF00E5FF
    ),
    PRO_PLAN(
        tierId = "tier_pro",
        title = "Pro Plan",
        priceBdtFormatted = "BDT 10,000",
        priceNumeric = 10000L,
        durationText = "Quarterly Pro Access",
        description = "Professional developer license for cyber engineers, power creators, and continuous NPU testing.",
        perks = listOf(
            "Unlimited Custom App Creation & Deployment",
            "Enhanced Quantum Key Rotation in VPN Sandbox",
            "Apex Terminal Advanced CLI Scripts",
            "Direct Technical Architect Assistance"
        ),
        badgeColorHex = 0xFFD500F9,
        isPopular = true
    ),
    STANDARD_FULL_OMNI(
        tierId = "tier_omni",
        title = "Standard Full Omni Access",
        priceBdtFormatted = "BDT 100,000",
        priceNumeric = 100000L,
        durationText = "Full Omni License",
        description = "Enterprise grade access with multi-seat testing, unrestricted bandwidth, and zero latency.",
        perks = listOf(
            "Omni Neural Pipeline Accelerator",
            "Private Local Enclave Encryption",
            "Enterprise Multi-Device Seat Deployment",
            "Verified Developer Badge on Created Apps"
        ),
        badgeColorHex = 0xFF00E676
    ),
    YEARLY_PLAN(
        tierId = "tier_yearly",
        title = "Yearly Plan",
        priceBdtFormatted = "BDT 1,000,000 / yr",
        priceNumeric = 1000000L,
        durationText = "Annual Comprehensive",
        description = "Annual corporate license including dedicated infrastructure nodes and direct automated feed updates.",
        perks = listOf(
            "365 Days Dedicated Quantum Core Access",
            "Automated Feed Sync Every 24 Hours",
            "VIP Developer Support via Direct SMS",
            "Custom Binary Signing & Packaging"
        ),
        badgeColorHex = 0xFFFFB300
    ),
    ELITE_LIFETIME_OMEGA(
        tierId = "tier_omega",
        title = "Elite / Lifetime / Omega",
        priceBdtFormatted = "BDT 10,000,000",
        priceNumeric = 10000000L,
        durationText = "Lifetime Sovereign",
        description = "Perpetual lifetime license for elite cyber pioneers. 1000 years certified quantum resilience.",
        perks = listOf(
            "Lifetime Unlimited App Store & Creator Rights",
            "Omega Tier Cryptographic Key Management",
            "Sovereign Sandbox Execution Privilege",
            "App Lifetime 1,000 Years Guaranteed"
        ),
        badgeColorHex = 0xFFFF1744
    ),
    UNLIMITED_NEURAL_QUANTUM(
        tierId = "tier_quantum_infinite",
        title = "Unlimited Neural Quantum Access",
        priceBdtFormatted = "BDT 1,000,000,000+",
        priceNumeric = 1000000000L,
        durationText = "Universal Infinite Node",
        description = "Infinite compute allocation, neural matrix synchronization, and supreme master tier.",
        perks = listOf(
            "Supreme Master Enclave Access",
            "Universal Neural Quantum Interconnect",
            "Custom Hardware Enclave Integration",
            "Mastercard & Sovereign Settlement Integration"
        ),
        badgeColorHex = 0xFFFFD700
    );

    companion object {
        fun fromId(id: String?): SubscriptionTier {
            return entries.firstOrNull { it.tierId == id } ?: TRIAL_60_DAYS
        }
    }
}
