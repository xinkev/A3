@file:OptIn(ExperimentalComposeUiApi::class)

package com.xinkev.keypad

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InterceptPlatformTextInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.xinkev.keypad.keypad.generated.resources.Res
import com.xinkev.keypad.keypad.generated.resources.add_notes
import kotlinx.coroutines.awaitCancellation
import org.jetbrains.compose.resources.stringResource


@Composable
fun KeypadInput(
    // TODO: Create a custom state class
    amountState: TextFieldState,
    modifier: Modifier = Modifier,
    noteState: TextFieldState,
    amountTextStyle: TextStyle = MaterialTheme.typography.displayMedium,
    noteTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
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
            textStyle = noteTextStyle,
            placeholder = stringResource(Res.string.add_notes),
        )
    }
}

@ExperimentalComposeUiApi
@Composable
private fun AmountInput(
    state: TextFieldState,
    textStyle: TextStyle
) {
    // Prevent soft keyboard from showing up
    // But have this issue: https://issuetracker.google.com/issues/372390044
    InterceptPlatformTextInput(
        interceptor = { _, _ ->
            awaitCancellation()
        },
    ) {
        BasicTextField(
            state = state,
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = textStyle.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp))
                .padding(
                    horizontal = KeypadDimens.keypadInputHorizontalPadding,
                    vertical = KeypadDimens.keypadInputVerticalPadding,
                )
                .fillMaxWidth()
        )
    }
}

@Composable
private fun NoteInput(
    state: TextFieldState,
    textStyle: TextStyle,
    placeholder: String? = null
) {
    BasicTextField(
        state = state,
        lineLimits = TextFieldLineLimits.MultiLine(1, 2),
        textStyle = textStyle.copy(textAlign = TextAlign.Center),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(45.dp))
            .padding(
                horizontal = KeypadDimens.keypadInputHorizontalPadding,
                vertical = KeypadDimens.keypadInputVerticalPadding,
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

@Preview
@Composable
private fun KeypadInput() {
    val textState = rememberTextFieldState("")
    KeypadInput(amountState = textState, noteState = textState)
}
