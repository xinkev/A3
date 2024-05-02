import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.AppDatabase
import data.DesktopDriverFactory
import data.DriverFactory

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "a3",
    ) {
        App(
            database = AppDatabase(DesktopDriverFactory())
        )
    }
}