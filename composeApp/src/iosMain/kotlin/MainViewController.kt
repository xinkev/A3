import androidx.compose.ui.window.ComposeUIViewController
import presentation.App
import di.iosModules
import di.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin(modules = iosModules) {
        App()
    }
}