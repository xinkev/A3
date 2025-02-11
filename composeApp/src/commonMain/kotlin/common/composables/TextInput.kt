package common.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.theme.Dimen

@Composable
fun TextInput(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.displaySmall,
    placeholder: String? = null,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp),
        contentColor = MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(Dimen.largeSize)
    ) {
        BasicTextField(
            state = state,
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = textStyle.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .padding(
                    horizontal = Dimen.largePadding,
                    vertical = Dimen.smallPadding,
                )
                .fillMaxWidth(),
            decorator = {
                if (state.text.isNotEmpty()) {
                    it()
                } else {
                    Box(contentAlignment = Alignment.Center) {
                        it()
                        Text(placeholder ?: "", style = textStyle.copy(color = Color.Gray))
                    }
                }
            }
        )
    }
}
