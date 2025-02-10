package feature.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import common.composables.CenteredTopBar
import feature.settings.composables.SettingsEntry
import feature.settings.composables.SettingsGroup
import feature.settings.composables.SettingsLabel
import kotlinx.coroutines.flow.collectLatest
import navigation.Route
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    vm: SettingsViewModel = koinViewModel(),
    navigateToAddCategory: () -> Unit,
) {
    SettingsScreenContent(
        vm,
        navigateToAddCategory
    )
}

@Composable
fun SettingsScreenContent(
    vm: ISettingsViewModel,
    navigateToAddCategory: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val scrollState = rememberScrollState()
    val loading by vm.loading.collectAsState()

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            vm.navigation.collectLatest {
                when (it) {
                    is Route.SettingsGraph.AddCategory -> navigateToAddCategory()
                    else -> {
                        // shouldn't happen. do nothing
                    }
                }
            }
        }
    }

    Scaffold(topBar = {
        CenteredTopBar("Settings")
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
            SettingsGroup("General") {
                AddCategory(onClick = vm::onClickAddCategory)
            }
            SettingsGroup("Backup") {
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
            SettingsLabel(Icons.Default.Restore, "Restore")
        }
    )
}

@Composable
private fun AddCategory(onClick: () -> Unit) {
    SettingsEntry(
        onClick,
        leftContent = {
            SettingsLabel(Icons.Default.Tag, "Add Category")
        },
        rightContent = {
            Icon(Icons.Default.ChevronRight, null)
        }
    )
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    SettingsScreenContent(PreviewSettingsViewModel, {})
}
