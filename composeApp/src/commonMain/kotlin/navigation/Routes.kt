package navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Home : Route
    @Serializable
    data object Settings: Route
    @Serializable
    data object ExpenseEditor: Route
}

