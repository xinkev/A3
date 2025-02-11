package core.event

import navigation.Route

sealed class NavigationEvent(val destination: Route?): A3Event {
    data object NavigateToAddCategory: NavigationEvent(Route.SettingsGraph.AddCategory)
    data object NavigateToExpenseEditor: NavigationEvent(Route.HomeGraph.ExpenseEditor)
    data object NavigateUp: NavigationEvent(null)
}
