package core.event

import navigation.Route

sealed class NavigationEvent(val destination: Route?): A3Event {
    data object NavigateUp: NavigationEvent(null)
    data class ChangeBottomTab(val route: Route): NavigationEvent(route)
    data object NavigateToCategories: NavigationEvent(Route.SettingsGraph.Categories)
    data object NavigateToExpenseEditor: NavigationEvent(Route.HomeGraph.ExpenseEditor)
    data object NavigateToCategoryEditor: NavigationEvent(Route.SettingsGraph.CategoryEditor)
}
