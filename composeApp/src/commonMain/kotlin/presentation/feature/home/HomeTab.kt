//package presentation.home
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Money
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.graphics.vector.rememberVectorPainter
//import cafe.adriel.voyager.navigator.Navigator
//import cafe.adriel.voyager.navigator.tab.Tab
//import cafe.adriel.voyager.navigator.tab.TabOptions
//
//object HomeTab : Tab {
//    override val options: TabOptions
//        @Composable
//        get() {
//            val title = "Expenses"
//            val icon = rememberVectorPainter(Icons.Default.Money)
//            return remember {
//                TabOptions(
//                    index = 0u,
//                    title = title,
//                    icon = icon
//                )
//            }
//        }
//
//    @Composable
//    override fun Content() {
//        Navigator(HomeScreen())
//    }
//}
//
//
