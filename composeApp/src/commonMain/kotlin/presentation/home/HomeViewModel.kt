package presentation.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.ExpenseDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import model.Expense

class HomeViewModel(
    expenseDataSource: ExpenseDataSource
) : ScreenModel {
    private val _expenses = MutableStateFlow(emptyList<Expense>())
    val expenses = expenseDataSource.getAll().stateIn(
        scope = screenModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )
}