package com.xinkev.keypad

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.UIKit.UIImpactFeedbackGenerator
import platform.UIKit.UIImpactFeedbackStyle.UIImpactFeedbackStyleLight

@Composable
actual fun rememberHapticFeedback() = remember {
    object : HapticFeedback {
        override fun vibrate() {
            val generator = UIImpactFeedbackGenerator(UIImpactFeedbackStyleLight)
            generator.impactOccurred()
        }
    }
}
