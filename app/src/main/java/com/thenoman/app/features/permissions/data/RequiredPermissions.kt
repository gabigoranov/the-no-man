package com.thenoman.app.features.permissions.data

import android.annotation.SuppressLint
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.ContentCopy

@SuppressLint("BatteryLife")
val listOfRequiredPermissions = listOf(
    PermissionItem(
        title = "Battery optimization",
        description = "Prevents monitoring from being interrupted in the background",
        icon = Icons.Default.BatteryAlert,
        iconDescription = "Battery alert",
        permissionIntent = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
    ),
    PermissionItem(
        title = "Accessibility",
        description = "Accessibility features are used to read text in LLM apps",
        icon = Icons.Default.Accessibility,
        iconDescription = "Accessibility",
        permissionIntent = Settings.ACTION_ACCESSIBILITY_SETTINGS,
        intentRequiresPackageExtension = false, // App will crash if set to true
    ),
    PermissionItem(
        title = "Overlay apps",
        description = "Custom overlays will be displayed during toxic interactions with LLMs",
        icon = Icons.Default.ContentCopy,
        iconDescription = "Overlaid window",
        permissionIntent = Settings.ACTION_MANAGE_OVERLAY_PERMISSION
    )
)
