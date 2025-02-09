package com.xinkev.keypad

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

sealed interface KeypadKey {
    val text: String
    val color: Color
        @Composable get
    val backgroundColor: Color
        @Composable get
}

data object BackspaceKey : KeypadKey {
    override val text: String = "⌫"
    override val color: Color
        @Composable get() = MaterialTheme.colorScheme.onError
    override val backgroundColor: Color
        @Composable get() = MaterialTheme.colorScheme.error
}

data object EqualKey : KeypadKey {
    override val text: String = "="
    override val color: Color
        @Composable get() = MaterialTheme.colorScheme.onPrimaryContainer
    override val backgroundColor: Color
        @Composable get() = MaterialTheme.colorScheme.primaryContainer
}

data class StandardKey(
    override val text: String,
) : KeypadKey {
    override val color: Color
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant
    override val backgroundColor: Color
        @Composable get() = MaterialTheme.colorScheme.surfaceVariant
}

enum class OperatorKey(
    override val text: String,
) : KeypadKey {
    Plus("+"),
    Minus("‒"),
    Multiply("×"),
    Divide("÷");

    override val backgroundColor: Color
        @Composable get() = MaterialTheme.colorScheme.secondary
    override val color: Color
        @Composable get() = MaterialTheme.colorScheme.onSecondary
}


enum class BracketKey(
    override val text: String
) : KeypadKey {
    Open("("), Close(")");

    override val backgroundColor: Color
        @Composable get() = MaterialTheme.colorScheme.secondary
    override val color: Color
        @Composable get() = MaterialTheme.colorScheme.onSecondary
}
