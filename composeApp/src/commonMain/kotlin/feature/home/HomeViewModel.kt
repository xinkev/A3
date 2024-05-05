package feature.home

import a3.composeapp.generated.resources.Res
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import database.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import model.Backup
import org.jetbrains.compose.resources.ExperimentalResourceApi

class HomeViewModel(
    private val db: Database,
) : ScreenModel {
    private val _data = MutableStateFlow(emptyList<Backup.Expense>())
    val data = _data.asStateFlow()

    @OptIn(ExperimentalResourceApi::class)
    fun onStart() {
        screenModelScope.launch(Dispatchers.IO) {
            // load json
            val json = Res.readBytes("files/sample.json")
            // convert the byte array to json
            val jsonString = json.decodeToString()
            _data.value =
                Json { ignoreUnknownKeys = true }.decodeFromString<Backup>(jsonString).expenses
        }
    }
}