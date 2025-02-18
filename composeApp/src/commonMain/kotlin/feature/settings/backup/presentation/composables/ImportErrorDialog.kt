package feature.settings.backup.presentation.composables

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.file_access_failed_error
import a3.composeapp.generated.resources.file_to_large_error
import a3.composeapp.generated.resources.ok
import a3.composeapp.generated.resources.restore_failed
import a3.composeapp.generated.resources.restore_unsupported_file_error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import feature.settings.backup.domain.model.ImportError
import feature.settings.backup.domain.model.ImportError.FileAccessError
import feature.settings.backup.domain.model.ImportError.FileTooLarge
import feature.settings.backup.domain.model.ImportError.UnrecognizedFile
import io.github.vinceglb.filekit.core.PlatformFile
import org.jetbrains.compose.resources.stringResource

@Composable
fun ImportErrorDialog(
    error: ImportError?,
    clearError: () -> Unit,
    file: PlatformFile?,
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
                val errorMsg = when (error) {
                    is FileAccessError -> {
                        stringResource(Res.string.file_access_failed_error, file?.name ?: "")
                    }

                    is FileTooLarge -> {
                        stringResource(Res.string.file_to_large_error)
                    }

                    is UnrecognizedFile -> {
                        stringResource(Res.string.restore_unsupported_file_error)
                    }
                }
                Text(errorMsg)
            }
        )
    }
}
