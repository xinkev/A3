package presentation.feature.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.composables.CenteredTopBar
import presentation.theme.Dimen

object SettingsScreen {

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
                    LinearProgressIndicator(
                        Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.primary)
                }
                Group("Backup") {
                    Import(onClick = vm::onClickRestore)
                }
            }
        }
    }

    @Composable
    private fun Import(onClick: () -> Unit) {
        SettingsEntry(
            onClick,
            leftContent = {
                LabelWithIcon(Icons.Default.Restore, "Restore")
            }
        )
    }

    @Composable
    private fun SettingsEntry(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        leftContent: @Composable RowScope.() -> Unit,
        rightContent: @Composable RowScope.() -> Unit = {}
    ) {
        Box(
            modifier = Modifier
                .height(56.dp)
                .clickable(onClick = onClick)
                .then(modifier)
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = Dimen.largePadding, vertical = Dimen.smallPadding
                    )
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(Dimen.mediumPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                leftContent()
                Spacer(Modifier.weight(1f))
                rightContent()
            }
        }
    }


    @Composable
    private fun LabelWithIcon(
        icon: ImageVector,
        label: String,
    ) {
        Row(
            modifier = Modifier.padding(vertical = Dimen.smallPadding)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.padding(end = Dimen.largePadding)
            )
            Text(
                label,
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.FirstLineTop,
                        ),
                    )
                ),
            )
        }
    }

    @Composable
    private fun Group(
        title: String,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        Column(modifier) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = Dimen.largePadding)
            )
            Spacer(Modifier.height(Dimen.mediumPadding))
            content()
        }
    }
}

@Preview
@Composable
private fun PreviewSettingsScreen() {
    SettingsScreen.View()
}