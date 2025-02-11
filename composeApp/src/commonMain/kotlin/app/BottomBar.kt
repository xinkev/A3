package app

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.home
import a3.composeapp.generated.resources.settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import core.event.EventBus
import core.event.NavigationEvent
import kotlinx.coroutines.launch
import navigation.Route
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun BottomBar(
    eventBus: EventBus = koinInject(),
    visible: Boolean,
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedItem by rememberSaveable { mutableStateOf(BottomBarItem.Home) }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { it },
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
        )
    ) {
        BottomAppBar(
            actions = {
                BottomBarItem.entries.forEach { item ->
                    NavigationBarItem(selected = item == selectedItem, icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(item.label)
                        )
                    }, label = {
                        Text(stringResource(item.label))
                    }, onClick = {
                        selectedItem = item
                        coroutineScope.launch {
                            eventBus.send(NavigationEvent.ChangeBottomTab(item.route))
                        }
                    })
                }
            },
        )
    }
}


enum class BottomBarItem(
    val label: StringResource, val icon: ImageVector, val route: Route
) {
    Home(
        label = Res.string.home,
        icon = Icons.Filled.Home,
        route = Route.HomeGraph.Home
    ),
    Settings(
        label = Res.string.settings,
        icon = Icons.Filled.Settings,
        route = Route.SettingsGraph.Settings
    )
}
