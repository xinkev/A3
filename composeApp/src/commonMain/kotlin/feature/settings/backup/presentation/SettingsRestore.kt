package feature.settings.backup.presentation

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.import_json
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
import feature.settings.backup.presentation.composables.ErrorDialog
import feature.settings.common.composables.SettingsEntry
import feature.settings.common.composables.SettingsLabel
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import io.github.vinceglb.filekit.core.PlatformFile
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SettingsImport(
    vm: ISettingsRestoreViewModel = settingsRestoreVM(),
) {
    var selectedFile by remember { mutableStateOf<PlatformFile?>(null) }

    val error by vm.error.collectAsState()
    val inProgress by vm.inProgress.collectAsState()
    val progressVisibility = remember(inProgress) {
        if (inProgress) 1f else 0f
    }

    val launcher = rememberFilePickerLauncher(
        type = PickerType.File(vm.supportedFormats.toList()),
        mode = PickerMode.Single,
    ) {
        it?.let {
            vm.onFilePicked(it)
            selectedFile = it
        }
    }

    ErrorDialog(error, vm::clearError, selectedFile)
    SettingsEntry(
        onClick = {
            launcher.launch()
        },
        leftContent = {
            SettingsLabel(Icons.Default.ArrowCircleDown, stringResource(Res.string.import_json))
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
private fun settingsRestoreVM(): ISettingsRestoreViewModel {
    return if (LocalInspectionMode.current) {
        PreviewSettingsRestoreViewModel
    } else {
        koinViewModel<SettingsRestoreViewModel>()
    }
}
