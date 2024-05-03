package home

import a3.composeapp.generated.resources.Res
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.KVStorage
import database.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import util.log

class HomeViewModel(
    private val db: Database,
    private val kvStorage: KVStorage,
) : ScreenModel {
    private val _data = MutableStateFlow("")
    val data = _data.asStateFlow()
    
    @OptIn(ExperimentalResourceApi::class)
    fun onStart() {
        screenModelScope.launch(Dispatchers.IO) {
            // load json
            val json = Res.readBytes("files/sample.json")
            // convert the byte array to json
            val jsonString = json.decodeToString()
            _data.value = db.getExpenses()
        }
    }

    fun onClick() {
        val seeded = kvStorage.seeded ?: false
        if (!seeded) {
            db.addExpense()
            kvStorage.seeded = true
        }
        _data.value = db.getExpenses()
    }
}