package com.xinkev.keypad

import androidx.compose.runtime.Composable

interface HapticFeedback {
    fun vibrate()
}

@Composable
expect fun rememberHapticFeedback(): HapticFeedback
