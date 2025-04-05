package feature.settings.backup.exporting.presentation.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.cancel
import a3.composeapp.generated.resources.file_overwrite_warning
import a3.composeapp.generated.resources.ok
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExportConfirmationDialog(
    show: Boolean,
    onCancel: () -> Unit,
    onOk: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (show) {
        AlertDialog(
            text = {
                Text(stringResource(Res.string.file_overwrite_warning))
            },
            onDismissRequest =  onDismiss,
            confirmButton = {
                TextButton(
                    onClick = onOk,
                    content = {
                        Text(stringResource(Res.string.ok))
                    }
                )
            },
            dismissButton = {
                TextButton(
                    onClick = onCancel,
                    content = { Text(stringResource(Res.string.cancel)) }
                )
            }
        )
    }
}
