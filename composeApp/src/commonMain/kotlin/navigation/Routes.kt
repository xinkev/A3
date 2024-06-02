package navigation

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.home
import a3.composeapp.generated.resources.settings
import a3.composeapp.generated.resources.expense_editor
import org.jetbrains.compose.resources.StringResource

enum class Route(val title: StringResource) {
    Home(Res.string.home),
    Settings(Res.string.settings),
    ExpenseEditor(Res.string.expense_editor)
}