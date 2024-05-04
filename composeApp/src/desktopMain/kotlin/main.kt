import a_.composeApp.BuildConfig
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.desktopModules
import di.startKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = BuildConfig.appName,
    ) {
        startKoin(modules = desktopModules) {
            App()
        }
    }
}