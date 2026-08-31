package com.thenoman.app.features.permissions.data

import androidx.compose.ui.graphics.vector.ImageVector

data class PermissionItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconDescription: String,
    val permissionIntent: String,
)