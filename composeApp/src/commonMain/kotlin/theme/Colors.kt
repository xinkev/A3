package theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val brown = Color(0xffc46935)
val textColor = Color(0xff333333)
val topBarBorderColor = Color(0xFFeeeeee)

val lightColors = lightColors(
    surface = brown,
    onSurface = Color.White,
    primary = Color.White,
    onPrimary = brown,
)