package com.thenoman.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    // Icon Sizes
    val iconSmall: Dp = 16.dp,
    val iconMedium: Dp = 24.dp, // Standard Material icon size
    val iconLarge: Dp = 32.dp,
    val iconExtraLarge: Dp = 48.dp,

    // Button & Component Heights
    val buttonHeight: Dp = 48.dp,
    val minTouchTarget: Dp = 48.dp, // Accessibility standard

    // Card & Surface Elevations
    val elevationNone: Dp = 0.dp,
    val elevationLow: Dp = 2.dp,
    val elevationMedium: Dp = 4.dp,
    val elevationHigh: Dp = 8.dp,

    // Border Widths
    val borderThin: Dp = 1.dp,
    val borderThick: Dp = 2.dp
)

// CompositionLocal for global access
val LocalDimensions = compositionLocalOf { Dimensions() }

// Extension property on MaterialTheme
val MaterialTheme.dimens: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current
