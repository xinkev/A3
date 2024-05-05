package presentation.settings

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
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import feature.theme.ListSpecs
import feature.theme.brownDarker
import uicomponents.CenteredTopBar

object SettingsTab : Tab {
    override val options: TabOptions
        @Composable get() {
            val icon = rememberVectorPainter(Icons.Default.Settings)
            return remember {
                TabOptions(
                    title = "Settings", icon = icon, index = 6u
                )
            }
        }

    @Composable
    override fun Content() {
        val scrollState = rememberScrollState()
        val vm = koinScreenModel<SettingsViewModel>()
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
                Separator()
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

    @Composable
    private fun Separator() {
        Divider(thickness = ListSpecs.dividerHeight, color = ListSpecs.dividerColour)
    }
}

@Preview
@Composable
private fun PreviewSettingsTab() {
    SettingsTab.Content()
}