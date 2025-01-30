@file:OptIn(ExperimentalComposeUiApi::class)

package presentation.feature.expense_editor.keypad

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.awaitCancellation


@Composable
fun KeypadInput(
    // TODO: Create a custom state class
    amountState: TextFieldState,
    modifier: Modifier = Modifier,
    noteState: TextFieldState,
    amountTextStyle: TextStyle = MaterialTheme.typography.displayMedium,
    noteTextStyle: TextStyle = MaterialTheme.typography.displaySmall,
) {
    Column(
        modifier = modifier
            .then(
                Modifier.clip(KeypadInputShape)
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp))
                    .width(IntrinsicSize.Max)
            ),
    ) {
        AmountInput(
            state = amountState,
            textStyle = amountTextStyle
        )
        NoteInput(
            state = noteState,
            textStyle = noteTextStyle
        )
    }
}

@Composable
private fun AmountInput(
    state: TextFieldState,
    textStyle: TextStyle
) {
    // Prevent soft keyboard from showing up
    InterceptPlatformTextInput(
        interceptor = { _, _ ->
            awaitCancellation()
        },
    ) {
        BasicInput(
            state = state,
            textStyle = textStyle.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@Composable
private fun NoteInput(
    state: TextFieldState,
    textStyle: TextStyle
) {
    BasicInput(
        state = state,
        textStyle = textStyle.copy(
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )
    )
}


@Composable
private fun BasicInput(
    state: TextFieldState,
    textStyle: TextStyle
) {
    BasicTextField(
        state = state,
        lineLimits = TextFieldLineLimits.MultiLine(1, 2),
        textStyle = textStyle,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(45.dp))
            .padding(
                horizontal = KeypadDimens.keypadInputHorizontalPadding,
                vertical = KeypadDimens.keypadInputVerticalPadding,
            )
            .fillMaxWidth()
    )
}

@Preview
@Composable
private fun KeypadInput() {
    val textState = rememberTextFieldState("")
    KeypadInput(amountState = textState, noteState = textState)
}