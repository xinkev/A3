package navigation

import feature.category.common.domain.model.Category
import feature.expense.common.domain.model.Expense
import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object HomeGraph : Route {
        @Serializable
        data object Home : Route

        @Serializable
        data class ExpenseEditor(
            val expense: Expense? = null,
            val initialDate: Long? = null,
        ) : Route
    }

    @Serializable
    data object SettingsGraph : Route {
        @Serializable
        data object Settings : Route

        @Serializable
        data object Categories : Route

        @Serializable
        data class CategoryEditor(val category: Category? = null) : Route
    }
}

