import androidx.compose.ui.window.ComposeUIViewController
import di.iosModules
import di.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin(modules = iosModules) {
        App()
    }
}