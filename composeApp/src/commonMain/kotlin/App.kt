import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import database.previewDriverFactory
import di.commonModule
import di.startKoin
import feature.home.HomeTab
import feature.settings.SettingsTab
import org.koin.dsl.module
import theme.A3Theme

@Composable
fun App() {
    A3Theme {
        TabNavigator(HomeTab) {
            Scaffold(
                content = {
                    CurrentTab()
                },
                bottomBar = {
                    BottomNavigation(elevation = 4.dp) {
                        TabNavigationItem(HomeTab)
                        TabNavigationItem(SettingsTab)
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current.key == tab.key,
        onClick = { tabNavigator.current = tab },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
    )
}

@Preview
@Composable
private fun PreviewApp() {
    val dbModule = module { single { previewDriverFactory } }
    startKoin(modules = listOf(), appDeclaration = {
        modules(commonModule + dbModule)
    }) {
        App()
    }
}