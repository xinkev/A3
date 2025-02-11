package app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.theme.A3Theme
import core.database.previewDatabaseFactory
import di.commonModule
import di.startKoin
import navigation.AppNavGraph
import org.koin.dsl.module

@Composable
fun App() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarVisible by remember {
        derivedStateOf {
            BottomBarItem.entries.any {
                backStackEntry?.destination?.hasRoute(it.route::class) == true
            }
        }
    }

    A3Theme {
        Scaffold(
            bottomBar = { BottomBar(visible = bottomBarVisible) },
        ) { paddings ->
            AppNavGraph(paddings, navController)
        }
    }
}


@Preview
@Composable
private fun PreviewApp() {
    val dbModule = module { single { previewDatabaseFactory } }
    startKoin(modules = listOf(), appDeclaration = {
        modules(commonModule + dbModule)
    }) {
        App()
    }
}
