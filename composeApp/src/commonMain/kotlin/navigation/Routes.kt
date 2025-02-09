package navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object HomeGraph : Route {
        @Serializable
        data object Home : Route

        @Serializable
        data object ExpenseEditor : Route
    }

    @Serializable
    data object SettingsGraph : Route {
        @Serializable
        data object Settings : Route

        @Serializable
        data object AddCategory : Route
    }
}

