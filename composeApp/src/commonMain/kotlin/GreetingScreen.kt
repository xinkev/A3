import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen

class GreetingScreen: Screen {
    @Composable
    override fun Content() {
        val greeting = remember { Greeting().greet() }
        Text(greeting)
    }
}