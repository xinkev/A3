import androidx.compose.ui.window.ComposeUIViewController
import core.App
import di.iosModules
import di.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin(modules = iosModules) {
        App()
    }
}
