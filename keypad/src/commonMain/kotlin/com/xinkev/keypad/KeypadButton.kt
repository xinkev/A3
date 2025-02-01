package com.xinkev.keypad

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.xinkev.keypad.KeypadDimens.defaultKeyIconSize
import com.xinkev.keypad.KeypadDimens.keypadMaxHeight
import com.xinkev.keypad.KeypadDimens.keypadMaxWidth
import com.xinkev.keypad.KeypadDimens.keypadMinHeight
import com.xinkev.keypad.KeypadDimens.keypadMinWidth


@Composable
fun RowScope.KeypadButton(
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    textStyle: TextStyle = MaterialTheme.typography.displaySmall,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    onClick: () -> Unit
) {
    KeypadButton(
        backgroundColor = backgroundColor,
        onClick = onClick,
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}

@Composable
fun RowScope.KeypadButton(
    icon: ImageVector,
    iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    contentDescription: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    iconSize: DpSize = defaultKeyIconSize,
    onClick: () -> Unit
) {
    KeypadButton(
        backgroundColor = backgroundColor,
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(iconSize),
            tint = iconColor
        )
    }
}

@Composable
private fun RowScope.KeypadButton(
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    // Make the buttons take equal space in a row by wrapping in an extra Box
    Box(modifier = Modifier.weight(1f)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .clickable(role = Role.Button) {
                    onClick.invoke()
                }
                .background(color = backgroundColor)
                .sizeIn(
                    minWidth = keypadMinWidth,
                    minHeight = keypadMinHeight,
                    maxHeight = keypadMaxHeight,
                    maxWidth = keypadMaxWidth,
                )
                .aspectRatio(1f, matchHeightConstraintsFirst = true),
            content = content
        )
    }
}

@Preview
@Composable
private fun TextKeyPadPreview() {
    MaterialTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(8.dp)) {
            KeypadButton("3") {}
            KeypadButton("4") {}
            KeypadButton("5") {}
            KeypadButton(icon = Icons.Outlined.Add) {}
        }
    }
}
