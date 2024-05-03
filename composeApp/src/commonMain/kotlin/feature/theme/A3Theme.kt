package feature.theme

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun A3Theme(
    content: @Composable () -> Unit
) {
//    val colors = lightColors

    MaterialTheme(
        colorScheme = lightColorScheme,
        shapes = shapes,
//        typography = a3Typography,
        content = {
            CompositionLocalProvider(LocalTextStyle provides a3TextStyle) {
                content()
            }
        }
    )
}
