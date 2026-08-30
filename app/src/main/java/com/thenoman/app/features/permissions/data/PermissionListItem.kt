package com.thenoman.app.features.permissions.data

import androidx.compose.ui.graphics.vector.ImageVector

data class PermissionListItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconDescription: String
)