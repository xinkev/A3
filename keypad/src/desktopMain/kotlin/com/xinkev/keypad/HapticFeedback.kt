package com.xinkev.keypad

import androidx.compose.runtime.Composable

@Composable
actual fun rememberHapticFeedback(): HapticFeedback {
    return object : HapticFeedback {
        override fun vibrate() {

        }
    }
}
