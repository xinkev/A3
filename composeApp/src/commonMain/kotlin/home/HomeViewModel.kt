package home

import cafe.adriel.voyager.core.model.ScreenModel
import database.Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val db: Database
) : ScreenModel {
    private val _data = MutableStateFlow("")
    val data = _data.asStateFlow()
    fun onStart() {
        db.addExpense()
        _data.value = db.getExpenses()
    }
}