package core

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.home
import a3.composeapp.generated.resources.settings
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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
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
                startDestination = Route.HomeGraph,
                modifier = Modifier.fillMaxSize()
                    .padding(it)
                    .consumeWindowInsets(it)
            ) {
                navigation<Route.HomeGraph>(startDestination = Route.HomeGraph.Home) {
                    composable<Route.HomeGraph.Home> {
                        HomeScreen(navigateToExpenseEditor = {
                            navController.navigate(Route.HomeGraph.ExpenseEditor)
                        })
                    }
                    composable<Route.HomeGraph.ExpenseEditor> {
                        ExpenseEditorScreen(
                            navigateUp = navController::navigateUp
                        )
                    }
                }

                navigation<Route.SettingsGraph>(startDestination = Route.SettingsGraph.Settings) {
                    composable<Route.SettingsGraph.Settings> {
                        SettingsScreen(navigateToAddCategory = {
                            navController.navigate(Route.SettingsGraph.AddCategory)
                        })
                    }
                    composable<Route.SettingsGraph.AddCategory> {
                        Text("AddCategory")
                    }
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
            BottomBarItem.entries.any {
                backStackEntry?.destination?.hasRoute(it.route::class) == true
            }
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
                        controller.navigate(item.route) {
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
