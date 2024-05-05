package feature.settings

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import theme.Dimen
import uicomponents.CenteredTopBar

object SettingsTab : Tab {
    @Composable
    override fun Content() {
        val scrollState = rememberScrollState()

        Scaffold(
            topBar = {
                CenteredTopBar("Settings")
            }
        ) {
            Column(
                modifier = Modifier.padding(it)
                    .verticalScroll(scrollState)
            ) {
                SettingsEntry()
                Divider()
            }
        }
    }

    @Composable
    private fun SettingsEntry() {
        Box(modifier = Modifier.clickable {}) {
            Row(modifier = Modifier.padding(Dimen.largePadding)) {
                Text("Backup/Restore")
                Spacer(Modifier.weight(1f))
                Icon(
                    modifier = Modifier.size(Dimen.mediumSize),
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = ""
                )
            }
        }
    }

    override val options: TabOptions
        @Composable get() {
            val icon = rememberVectorPainter(Icons.Default.Settings)
            return remember {
                TabOptions(
                    title = "Settings", icon = icon, index = 6u
                )
            }
        }
}

@Preview
@Composable
fun PreviewSettingsTab() {
    SettingsTab.Content()
}