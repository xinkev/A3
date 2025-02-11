package feature.settings

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.categories
import a3.composeapp.generated.resources.data
import a3.composeapp.generated.resources.general
import a3.composeapp.generated.resources.import
import a3.composeapp.generated.resources.settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import common.composables.CenteredTopBar
import feature.settings.composables.SettingsEntry
import feature.settings.composables.SettingsGroup
import feature.settings.composables.SettingsLabel
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

    Scaffold(topBar = {
        CenteredTopBar(stringResource(Res.string.settings))
    }) {
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
                Restore(onClick = vm::onClickRestore)
            }
        }
    }
}

@Composable
private fun Restore(onClick: () -> Unit) {
    SettingsEntry(
        onClick,
        leftContent = {
            SettingsLabel(Icons.Default.ArrowCircleDown, stringResource(Res.string.import))
        }
    )
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
    SettingsScreenContent(PreviewSettingsViewModel)
}
