package feature.settings.backup.exporting.presentation.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.directory_creation_failed_error
import a3.composeapp.generated.resources.export_unknown_error
import a3.composeapp.generated.resources.file_create_failed_error
import a3.composeapp.generated.resources.file_write_failed_error
import a3.composeapp.generated.resources.folder_write_access_denied_error
import a3.composeapp.generated.resources.ok
import a3.composeapp.generated.resources.restore_failed
import a3.composeapp.generated.resources.storage_space_insufficient_error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import core.file.FileWriteError
import feature.settings.backup.exporting.domain.model.ExportError
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExportErrorDialog(
    error: ExportError?,
    clearError: () -> Unit,
) {
    if (error != null) {
        AlertDialog(
            onDismissRequest = clearError,
            confirmButton = {
                TextButton(clearError) {
                    Text(stringResource(Res.string.ok))
                }
            },
            properties = DialogProperties(dismissOnClickOutside = true),
            title = {
                Text(stringResource(Res.string.restore_failed))
            },
            text = {
                val errorMsgRes = when (error) {
                    FileWriteError.AccessDenied -> Res.string.folder_write_access_denied_error
                    FileWriteError.DirectoryCreationFailed -> Res.string.directory_creation_failed_error
                    FileWriteError.FileWriteFailed -> Res.string.file_write_failed_error
                    FileWriteError.FileCreateFailed -> Res.string.file_create_failed_error
                    FileWriteError.StorageSpaceInsufficient -> Res.string.storage_space_insufficient_error
                    FileWriteError.Unknown -> Res.string.export_unknown_error
                }
                Text(stringResource(errorMsgRes))
            }
        )
    }
}
