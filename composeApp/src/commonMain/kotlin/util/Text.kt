package util

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.LineHeightStyle

val verticallyCenteredTextStyle
    @Composable get() = LocalTextStyle.current.copy(
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.Both,
        )
    )