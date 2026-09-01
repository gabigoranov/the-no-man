package com.thenoman.app.features.accessibility

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.view.accessibility.AccessibilityEvent

//TODO: SETUP autostart on entering certain apps, etc.
@SuppressLint("AccessibilityPolicy")
class ScreenReaderService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // Interpret the event and provide feedback to the user
    }

    override fun onInterrupt() {
        // Interrupt any ongoing feedback
    }

    override fun onServiceConnected() {
        // Perform initialization here
    }
}