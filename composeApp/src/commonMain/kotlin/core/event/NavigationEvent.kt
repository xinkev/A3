package core.event

import feature.category.common.domain.model.Category
import feature.expense.common.domain.model.Expense
import navigation.Route

sealed class NavigationEvent(
    val destination: Route?
) : A3Event {
    data object NavigateUp : NavigationEvent(null)
    data class ChangeBottomTab(val route: Route) : NavigationEvent(route)
    data object NavigateToCategories : NavigationEvent(Route.SettingsGraph.Categories)
    data class NavigateToExpenseEditor(val expense: Expense? = null) : NavigationEvent(Route.HomeGraph.ExpenseEditor(expense))
    data class NavigateToCategoryEditor(val category: Category?) :
        NavigationEvent(Route.SettingsGraph.CategoryEditor(category))
}
