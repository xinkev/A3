import a_.composeApp.BuildConfig
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import database.Database
import database.DesktopDriverFactory
import di.startKoin
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

fun main() = application {
    val db = Database(DesktopDriverFactory())

    Window(
        onCloseRequest = ::exitApplication,
        title = BuildConfig.appName,
    ) {
        startKoin {
            App(db)
        }
    }
}