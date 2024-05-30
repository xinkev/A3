package feature.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

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
            content()
        }
    )
}
