package com.xinkev.keypad

import android.content.Context
import android.os.VibrationEffect
import android.os.VibrationEffect.EFFECT_TICK
import android.os.Vibrator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class HapticFeedbackImpl(context: Context) : HapticFeedback {
    private val vibrator = context.getSystemService(Vibrator::class.java)

    override fun vibrate() {
        if (android.os.Build.VERSION.SDK_INT < 29) {
            vibrator.vibrate(1)
        } else {
            vibrator.vibrate(VibrationEffect.createPredefined(EFFECT_TICK))
        }
    }
}

@Composable
actual fun rememberHapticFeedback(): HapticFeedback {
    val context = LocalContext.current.applicationContext
    return remember {
        HapticFeedbackImpl(context)
    }
}
