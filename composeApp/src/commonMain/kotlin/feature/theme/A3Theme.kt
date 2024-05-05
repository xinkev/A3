package feature.theme

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun A3Theme(
    content: @Composable () -> Unit
) {
    val colors = lightColors

    MaterialTheme(
        colors = colors,
        shapes = shapes,
        typography = a3Typography,
        content = {
            CompositionLocalProvider(LocalTextStyle provides a3TextStyle) {
                content()
            }
        }
    )
}
