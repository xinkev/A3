package navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import common.util.HandleEvents
import core.event.NavigationEvent
import feature.category.editor.CategoryEditorScreen
import feature.category.categories.CategoriesScreen
import feature.expense.editor.presentation.ExpenseEditorScreen
import feature.home.presentation.HomeScreen
import feature.settings.SettingsScreen

@Composable
fun AppNavGraph(
    scaffoldPaddings: PaddingValues,
    navController: NavHostController,
) {
    HandleEvents<NavigationEvent> {
        when (it) {
            is NavigationEvent.NavigateUp -> {
                navController.navigateUp()
            }

            is NavigationEvent.ChangeBottomTab -> {
                navController.navigate(it.route) {
                    popUpTo(navController.graph.findStartDestination().route!!) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }

            else -> {
                navController.navigate(it.destination!!)
            }
        }
    }

    NavHost(
        navController,
        startDestination = Route.HomeGraph,
        modifier = Modifier.fillMaxSize()
            .padding(scaffoldPaddings)
            .consumeWindowInsets(scaffoldPaddings)
    ) {
        navigation<Route.HomeGraph>(startDestination = Route.HomeGraph.Home) {
            composable<Route.HomeGraph.Home> {
                HomeScreen()
            }
            composable<Route.HomeGraph.ExpenseEditor> {
                ExpenseEditorScreen()
            }
        }

        navigation<Route.SettingsGraph>(startDestination = Route.SettingsGraph.Settings) {
            composable<Route.SettingsGraph.Settings> {
                SettingsScreen()
            }
            composable<Route.SettingsGraph.Categories> {
                CategoriesScreen()
            }
            composable<Route.SettingsGraph.CategoryEditor> {
                CategoryEditorScreen()
            }
        }
    }
}
