package presentation.feature.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import presentation.theme.ListSpecs
import presentation.theme.brownDarker
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.composables.CenteredTopBar

object SettingsScreen{

    @OptIn(KoinExperimentalAPI::class)
    @Composable
    fun View(
        vm: SettingsViewModel = koinViewModel()
    ) {
        val scrollState = rememberScrollState()
        val loading by vm.loading.collectAsState()

        Scaffold(topBar = {
            CenteredTopBar("Settings")
        }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .verticalScroll(scrollState)
            ) {
                AnimatedVisibility(visible = loading) {
                    LinearProgressIndicator(Modifier.fillMaxWidth(), color = brownDarker)
                }
                Import(onClick = vm::onClickRestore)
                HorizontalDivider()
            }
        }
    }

    @Composable
    private fun Import(onClick: () -> Unit) {
        LabelOnlySettingsEntry("Import", modifier = Modifier.clickable { onClick() })
    }

    @Composable
    private fun LabelOnlySettingsEntry(
        label: String,
        modifier: Modifier,
    ) {
        SettingsEntry(modifier = modifier, leftContent = {
            Text(
                label, style = ListSpecs.textStyle
            )
        }, rightContent = {})
    }

    @Composable
    private fun SettingsEntry(
        modifier: Modifier,
        leftContent: @Composable () -> Unit,
        rightContent: @Composable () -> Unit
    ) {
        Box(modifier = Modifier.height(ListSpecs.height).then(modifier)) {
            Row(
                modifier = Modifier.padding(
                    horizontal = ListSpecs.horizontalPadding, vertical = ListSpecs.verticalPadding
                ), horizontalArrangement = Arrangement.spacedBy(ListSpecs.horizontalPadding)
            ) {
                leftContent()
                Spacer(Modifier.weight(1f))
                rightContent()
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    SettingsScreen.View()
}