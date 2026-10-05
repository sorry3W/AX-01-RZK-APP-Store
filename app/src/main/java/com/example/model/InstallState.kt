package com.example.model

sealed interface InstallState {
    data object NotInstalled : InstallState
    data class Downloading(val progress: Float, val speedKbps: Int = 1420) : InstallState
    data object Installed : InstallState
    data object UpdateAvailable : InstallState
}
