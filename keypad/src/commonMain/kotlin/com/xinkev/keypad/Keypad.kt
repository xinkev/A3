package com.xinkev.keypad

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val keys = listOf(
    OperatorKey("("),
    OperatorKey(")"),
    OperatorKey("-"),
    OperatorKey("+"),

    StandardKey("7"),
    StandardKey("8"),
    StandardKey("9"),
    OperatorKey("÷"),

    StandardKey("4"),
    StandardKey("5"),
    StandardKey("6"),
    OperatorKey("×"),

    StandardKey("1"),
    StandardKey("2"),
    StandardKey("3"),
    BackspaceKey,

    StandardKey("000"),
    StandardKey("."),
    StandardKey("0"),
    EqualsKey,
)

private const val CELLS_PER_ROW = 4

@Composable
fun Keypad(
    modifier: Modifier = Modifier,
    onClickKey: (KeypadKey) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(KeypadDimens.keyPadInnerPadding)
            .sizeIn(maxWidth = KeypadDimens.maxKeypadWidth)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(KeypadDimens.rowGap)
    ) {
        // Every row has fixed number of keys so take a chunk of CELLS_PER_ROW and loop over
        keys.chunked(CELLS_PER_ROW).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(KeypadDimens.cellGap)) {
                row.forEach { key ->
                    KeypadButton(
                        text = key.text,
                        backgroundColor = key.backgroundColor,
                        textColor = key.color,
                        onClick = { onClickKey(key) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun KeypadPreview() {
    MaterialTheme {
        Keypad {}
    }
}
