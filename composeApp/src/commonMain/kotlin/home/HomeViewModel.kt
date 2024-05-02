package home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val db: AppDatabase
) : ScreenModel {
    private val _data = MutableStateFlow("")
    val data = _data.asStateFlow()
    fun onStart() {
        db.addExpense()
        _data.value = db.getExpenses()
    }
}