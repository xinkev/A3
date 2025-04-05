package feature.settings.presentation

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.categories
import a3.composeapp.generated.resources.data
import a3.composeapp.generated.resources.export_success
import a3.composeapp.generated.resources.general
import a3.composeapp.generated.resources.restore_success
import a3.composeapp.generated.resources.settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import common.composables.CenteredTopBar
import common.util.HandleEvents
import common.util.preview
import feature.settings.backup.exporting.presentation.SettingsExport
import feature.settings.backup.importing.presentation.SettingsImport
import feature.settings.common.composables.SettingsEntry
import feature.settings.common.composables.SettingsGroup
import feature.settings.common.composables.SettingsLabel
import feature.settings.common.event.SettingsEvent
import feature.settings.common.event.SettingsEvent.ExportSuccess
import feature.settings.common.event.SettingsEvent.RestoreSuccess
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    vm: SettingsViewModel = koinViewModel(),
) {
    SettingsScreenContent(
        vm,
    )
}

@Composable
fun SettingsScreenContent(
    vm: ISettingsViewModel,
) {
    val scrollState = rememberScrollState()
    val loading by vm.loading.collectAsState()
    val snackbarState = remember { SnackbarHostState() }
    val restoreSuccessMsg = stringResource(Res.string.restore_success)
    val exportSuccessMsg = stringResource(Res.string.export_success)
    HandleEvents<SettingsEvent> {
        when(it) {
            RestoreSuccess -> snackbarState.showSnackbar(message = restoreSuccessMsg)
            ExportSuccess -> snackbarState.showSnackbar(message = exportSuccessMsg)
        }
    }

    Scaffold(
        topBar = {
            CenteredTopBar(stringResource(Res.string.settings))
        },
        snackbarHost = {
            SnackbarHost(snackbarState)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            AnimatedVisibility(visible = loading) {
                LinearProgressIndicator(
                    Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            SettingsGroup(stringResource(Res.string.general)) {
                AddCategory(onClick = vm::onClickAddCategory)
            }
            SettingsGroup(stringResource(Res.string.data)) {
                SettingsImport()
                SettingsExport()
            }
        }
    }
}

@Composable
private fun AddCategory(onClick: () -> Unit) {
    SettingsEntry(
        onClick,
        leftContent = {
            SettingsLabel(Icons.Default.Category, stringResource(Res.string.categories))
        },
        rightContent = {
            Icon(Icons.Default.ChevronRight, null)
        }
    )
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    preview {
        SettingsScreenContent(PreviewSettingsViewModel)
    }
}
