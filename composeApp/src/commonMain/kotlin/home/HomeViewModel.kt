package home

import cafe.adriel.voyager.core.model.ScreenModel
import data.KVStorage
import database.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val db: Database,
    private val kvStorage: KVStorage,
) : ScreenModel {
    private val _data = MutableStateFlow("")
    val data = _data.asStateFlow()
    fun onStart() {
        _data.value = db.getExpenses()
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