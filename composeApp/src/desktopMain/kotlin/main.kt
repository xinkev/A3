
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.xinkev.a3.BuildConfig
import di.desktopModules
import di.startKoin
import presentation.App

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