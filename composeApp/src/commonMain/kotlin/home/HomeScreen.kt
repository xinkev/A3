package home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.KVStorageImpl
import database.Database

class HomeScreen(private val db: Database) : Screen {
    private  val vm by lazy {
        HomeViewModel(db = db, kvStorage = KVStorageImpl())
    }
    @Composable
    override fun Content() {
        val vm = rememberScreenModel { vm }
        val data = vm.data.collectAsState()

        LifecycleEffect(
            onStarted = vm::onStart
        )

        Column {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(data.value)
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = vm::onClick,
                    content = { Text("Click this") }
                )
            }
        }
    }
}