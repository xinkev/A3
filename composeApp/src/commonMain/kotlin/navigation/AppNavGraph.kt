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
import feature.category.categoryEditor.CategoryEditorScreen
import feature.category.categoryList.CategoriesScreen
import feature.expense.expenseEditor.ExpenseEditorScreen
import feature.expense.expenseList.ExpenseListScreen
import feature.settings.presentation.SettingsScreen
import navigation.types.categoryNavTypeMap
import navigation.types.expenseTypeMap

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
        startDestination = Route.ExpenseGraph,
        modifier = Modifier.fillMaxSize()
            .padding(scaffoldPaddings)
            .consumeWindowInsets(scaffoldPaddings)
    ) {
        navigation<Route.ExpenseGraph>(startDestination = Route.ExpenseGraph.ExpenseList) {
            composable<Route.ExpenseGraph.ExpenseList> {
                ExpenseListScreen()
            }
            composable<Route.ExpenseGraph.ExpenseEditor>(typeMap = expenseTypeMap) {
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
            composable<Route.SettingsGraph.CategoryEditor>(
                typeMap = categoryNavTypeMap
            ) {
                CategoryEditorScreen()
            }
        }
    }
}

