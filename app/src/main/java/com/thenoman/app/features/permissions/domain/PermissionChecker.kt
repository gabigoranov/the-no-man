package com.thenoman.app.features.permissions.domain

import android.annotation.SuppressLint
import android.content.Context
import android.os.PowerManager
import android.provider.Settings
import android.view.accessibility.AccessibilityManager

class PermissionChecker(private val context: Context) {

    @SuppressLint("BatteryLife")
    fun checkPermissionForIntent(intent: String): Boolean {
        return when(intent) {
            Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS -> isBatteryOptimizationDisabled()
            Settings.ACTION_ACCESSIBILITY_SETTINGS -> isAccessibilityGranted()
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION -> isAppOverlayGranted()
            else -> throw IllegalArgumentException("Please pass a valid intent into the PermissionChecker")
        }
    }

    fun isBatteryOptimizationDisabled(): Boolean {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager

        return pm.isIgnoringBatteryOptimizations(context.packageName)
    }

    fun isAccessibilityGranted(): Boolean {
        val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

        return am.isEnabled
    }

    fun isAppOverlayGranted(): Boolean {
        return Settings.canDrawOverlays(context)
    }
}
