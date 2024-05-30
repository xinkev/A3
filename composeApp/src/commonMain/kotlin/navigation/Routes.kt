package navigation

import a3.composeapp.generated.resources.Res
import a3.composeapp.generated.resources.home
import a3.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.StringResource

enum class Route(val title: StringResource) {
    Home(Res.string.home),
    Settings(Res.string.settings),
}