package feature.category.editor.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.cancel
import a3.composeapp.generated.resources.delete
import a3.composeapp.generated.resources.delete_category_warning
import a3.composeapp.generated.resources.edit_category
import a3.composeapp.generated.resources.new_category
import a3.composeapp.generated.resources.yes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import common.composables.TopBar
import org.jetbrains.compose.resources.stringResource

@Composable
fun CategoryEditorScreenTopBar(
    isEdit: Boolean,
    addButtonEnabled: Boolean,
    onDeleteConfirmed: () -> Unit,
) {
    var showConfirmation by remember { mutableStateOf(false) }
    if (showConfirmation) {
        AlertDialog(
            onDismissRequest = { showConfirmation = false },
            text = {
                Text(stringResource(Res.string.delete_category_warning))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmation = false
                        onDeleteConfirmed.invoke()
                    },
                    content = {
                        Text(stringResource(Res.string.yes), color = Color.Red)
                    }
                )
            },
            dismissButton = {
                TextButton(onClick = { showConfirmation = false }) {
                    Text(stringResource(Res.string.cancel))
                }
            }
        )
    }
    TopBar(
        title = stringResource(if (isEdit) Res.string.edit_category else Res.string.new_category),
        actions = {
            if (isEdit) {
                TextButton(
                    enabled = addButtonEnabled,
                    onClick = { showConfirmation = true },
                    content = {
                        Text(stringResource(Res.string.delete))
                    }
                )
            }
        }
    )
}
