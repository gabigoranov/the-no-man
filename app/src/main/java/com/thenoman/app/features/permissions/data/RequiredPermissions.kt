package com.thenoman.app.features.permissions.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.BatteryAlert

val requiredPermissions = listOf(
    PermissionListItem(
        title = "Battery optimization",
        description = "Prevents monitoring from being interrupted in the background",
        icon = Icons.Default.BatteryAlert,
        iconDescription = "Battery alert"
    ),
    PermissionListItem(
        title = "Accessibility",
        description = "Accessibility features are used to read text in LLM apps",
        icon = Icons.Default.Accessibility,
        iconDescription = "Accessibility"
    )
)
