package common.composables

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import core.event.EventBus
import core.event.NavigationEvent
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIconHidden: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    onClickBack: () -> Unit = {},
    eventBus: EventBus = koinInject()
) {
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        modifier = modifier,
        title = { Text(title) },
        navigationIcon = {
            if (!navigationIconHidden) {
                IconButton(onClick = {
                    onClickBack()
                    coroutineScope.launch {
                        eventBus.send(NavigationEvent.NavigateUp)
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            }
        },
        actions = actions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredTopBar(
    title: String,
    modifier: Modifier = Modifier,
    rightContent: @Composable () -> Unit = {},
    leftContent: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(title) },
        navigationIcon = rightContent,
        actions = leftContent
    )
}
