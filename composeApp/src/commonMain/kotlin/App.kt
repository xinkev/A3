import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import data.AppDatabase
import home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(database: AppDatabase) {
    MaterialTheme {
        Navigator(HomeScreen(database))
    }
}

