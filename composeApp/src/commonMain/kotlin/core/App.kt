package core

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import database.previewDatabaseFactory
import di.commonModule
import di.startKoin
import feature.expense.editor.presentation.ExpenseEditorScreen
import feature.home.presentation.HomeScreen
import feature.settings.SettingsScreen
import navigation.Route
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.dsl.module
import theme.A3Theme

@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    A3Theme {
        Scaffold(
            bottomBar = { BottomBar(navController) },
        ) {
            NavHost(
                navController,
                startDestination = Route.Home.name,
                modifier = Modifier.fillMaxSize()
                    .padding(it)
                    .consumeWindowInsets(it)
            ) {
                composable(Route.Home.name) {
                    HomeScreen(navigateToExpenseEditor = {
                        navController.navigate(Route.ExpenseEditor.name)
                    })
                }
                composable(Route.Settings.name) {
                    SettingsScreen()
                }
                composable(Route.ExpenseEditor.name) {
                    ExpenseEditorScreen(
                        navigateUp = navController::navigateUp
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomBar(controller: NavHostController) {
    var selectedItem by rememberSaveable { mutableStateOf(BottomBarItem.Home) }

    val backStackEntry by controller.currentBackStackEntryAsState()
    val visible by remember {
        derivedStateOf {
            backStackEntry?.destination?.route != Route.ExpenseEditor.name
        }
    }

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
                        controller.navigate(item.route.name) {
                            popUpTo(controller.graph.findStartDestination().route!!) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
                }
            },
        )
    }
}

private enum class BottomBarItem(
    val label: StringResource, val icon: ImageVector, val route: Route
) {
    Home(
        label = Route.Home.title,
        icon = Icons.Filled.Home,
        route = Route.Home
    ),
    Settings(label = Route.Settings.title, icon = Icons.Filled.Settings, route = Route.Settings)
}

@Preview
@Composable
private fun PreviewApp() {
    val dbModule = module { single { previewDatabaseFactory } }
    startKoin(modules = listOf(), appDeclaration = {
        modules(commonModule + dbModule)
    }) {
        App()
    }
}
