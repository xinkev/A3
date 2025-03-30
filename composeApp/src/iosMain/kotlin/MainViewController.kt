import androidx.compose.ui.window.ComposeUIViewController
import app.App
import di.iosModules
import common.di.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin(modules = iosModules) {
        App()
    }
}
