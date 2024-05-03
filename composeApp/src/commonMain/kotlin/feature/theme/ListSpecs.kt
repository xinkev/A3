package feature.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

object ListSpecs {
    val textStyle: TextStyle
        @Composable
        get() = MaterialTheme.typography.bodyLarge
    val iconSize = 24.dp
    val dividerColour = dividerColor
    val indent = 16.dp
    val height = 56.dp
    val twoLineHeight = 72.dp
    val horizontalPadding = Dimen.mediumPadding
    val verticalPadding = Dimen.largePadding
}