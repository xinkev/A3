package com.xinkev.keypad

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow

@Stable
data class KeypadState(
    val amount: TextFieldState = TextFieldState(),
    val note: TextFieldState = TextFieldState()
) {
    var amountEvalError by mutableStateOf("")
    val isValid: Boolean
        get() = amountEvalError.isEmpty()

    fun amountAsFlow() = snapshotFlow { amount.text }

    fun clear() {
        amount.clearText()
        note.clearText()
        amountEvalError = ""
    }
}
