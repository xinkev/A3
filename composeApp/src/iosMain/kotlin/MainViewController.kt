import androidx.compose.ui.window.ComposeUIViewController
import data.AppDatabase
import data.IosDriverFactory

fun MainViewController() = ComposeUIViewController {
    App(database = AppDatabase(IosDriverFactory()))
}