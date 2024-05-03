import androidx.compose.ui.window.ComposeUIViewController
import database.Database
import database.IOSDriverFactory
import di.startKoin

fun MainViewController() = ComposeUIViewController {
    val db = Database(IOSDriverFactory())
    startKoin {
        App(db)
    }
}