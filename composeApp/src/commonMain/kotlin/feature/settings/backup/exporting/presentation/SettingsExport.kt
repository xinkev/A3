package feature.settings.backup.exporting.presentation

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.export_json
import a3.composeapp.generated.resources.select_directory_to_export_title
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalInspectionMode
import app.theme.Dimen
import feature.settings.backup.exporting.presentation.composables.ExportConfirmationDialog
import feature.settings.backup.exporting.presentation.composables.ExportErrorDialog
import feature.settings.common.composables.SettingsEntry
import feature.settings.common.composables.SettingsLabel
import io.github.vinceglb.filekit.compose.rememberDirectoryPickerLauncher
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsExport(
    vm: ISettingsExportViewModel = settingsExportVM(),
) {
    val error by vm.error.collectAsState()
    val inProgress by vm.inProgress.collectAsState()
    var showConfirmationDialog by remember { mutableStateOf(false) }
    val progressVisibility = remember(inProgress) {
        if (inProgress) 1f else 0f
    }

    val launcher = rememberDirectoryPickerLauncher(
        title = stringResource(Res.string.select_directory_to_export_title),
    ) {
        it?.let {
            vm.onDirectoryChosen(it)
            showConfirmationDialog = true
        }
    }

    ExportConfirmationDialog(
        show = showConfirmationDialog,
        onCancel = {
            showConfirmationDialog = false
            vm.onCancel()
        },
        onOk = {
            showConfirmationDialog = false
            vm.onConfirmation()
        },
        onDismiss = { showConfirmationDialog = false }
    )
    ExportErrorDialog(error, vm::clearError)
    SettingsEntry(
        onClick = {
            launcher.launch()
        },
        leftContent = {
            SettingsLabel(Icons.Default.ArrowCircleDown, stringResource(Res.string.export_json))
        },
        rightContent = {
            CircularProgressIndicator(
                modifier = Modifier
                    .alpha(progressVisibility)
                    .size(Dimen.largeSize)
            )
        },
        enabled = !inProgress
    )
}

@Composable
private fun settingsExportVM(): ISettingsExportViewModel {
    return if (LocalInspectionMode.current) {
        PreviewSettingsExportViewModel
    } else {
        koinViewModel<SettingsExportViewModel>()
    }
}
