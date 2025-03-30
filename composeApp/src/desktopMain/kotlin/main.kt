import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import app.App
import com.xinkev.a3.BuildConfig
import di.desktopModules
import common.di.startKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = BuildConfig.appName,
        state = rememberWindowState(
            size = DpSize(350.dp, 700.dp)
        )
    ) {
        startKoin(modules = desktopModules) {
            App()
        }
    }
}
