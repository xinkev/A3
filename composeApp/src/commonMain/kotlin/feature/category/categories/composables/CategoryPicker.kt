package feature.category.categories.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.cancel
import a3.composeapp.generated.resources.ok
import a3.composeapp.generated.resources.select_category
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.window.Dialog
import common.domain.model.A3Icon.MaterialIcon
import feature.category.common.domain.model.Category
import org.jetbrains.compose.resources.stringResource
import app.theme.Dimen


@Composable
fun CategoryPicker(
    modifier: Modifier = Modifier,
    onSelected: (Category?) -> Unit,
    initialSelectedCategory: Category?
) {
    var openDialog by remember { mutableStateOf(false) }
    var newSelection by remember { mutableStateOf(initialSelectedCategory) }
    // Remember the current selection to restore it if the user cancels the dialog.
    // Set initialSelectedCategory as the key to refresh the value when it changes.
    val currentSelection = remember(initialSelectedCategory) { initialSelectedCategory }

    if (openDialog) {
        CategoryPickerDialog(
            onDismissRequest = {
                openDialog = false
                onSelected(newSelection)
            },
            onSelectionChange = {
                newSelection = it
            },
            onClickOk = {
                onSelected(newSelection)
            },
            onClickCancel = {
                newSelection = currentSelection
            },
            selectedCategory = newSelection
        )
    }

    CategoryPickerButton(
        modifier = modifier,
        onClick = { openDialog = true },
        // The button should always show the current selection, even if the dialog is open.
        selectedCategory = currentSelection
    )
}

@Composable
fun CategoryPickerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    selectedCategory: Category?
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        if (selectedCategory != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimen.xSmallPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedCategory.icon != null) {
                    Icon(
                        imageVector = selectedCategory.icon.vector(),
                        contentDescription = null
                    )
                }
                Text(selectedCategory.name)
            }
        } else {
            Text(stringResource(Res.string.select_category))
        }
    }
}

@Composable
private fun CategoryPickerDialog(
    onDismissRequest: () -> Unit,
    onSelectionChange: (Category?) -> Unit,
    onClickOk: () -> Unit,
    onClickCancel: () -> Unit,
    selectedCategory: Category?
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraLarge)
                .background(MaterialTheme.colorScheme.background)
        ) {
            CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onBackground) {
                Categories(
                    modifier = Modifier.padding(Dimen.largePadding),
                    selected = selectedCategory,
                    onSelectionChange = onSelectionChange,
                    hasBorder = false
                )
                Row(modifier = Modifier.align(Alignment.End)) {
                    TextButton(
                        onClick = {
                            onClickCancel.invoke()
                            onDismissRequest.invoke()
                        },
                        content = { Text(stringResource(Res.string.cancel)) }
                    )
                    TextButton(
                        onClick = {
                            onClickOk.invoke()
                            onDismissRequest.invoke()
                        },
                        content = { Text(stringResource(Res.string.ok)) }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun CategoryPickerDialogPreview() {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        CategoryPickerDialog(
            onDismissRequest = {},
            selectedCategory = null,
            onSelectionChange = {},
            onClickOk = {},
            onClickCancel = {}
        )
    }
}

@Preview
@Composable
private fun CategoryPickerPreview() {
    CompositionLocalProvider(LocalInspectionMode provides true) {
        CategoryPicker(
            initialSelectedCategory = Category("Test", icon = MaterialIcon(Icons.Default.Info)),
            onSelected = {}
        )
    }
}
