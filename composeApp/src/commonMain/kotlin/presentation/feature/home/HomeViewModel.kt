package presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.ExpenseDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import presentation.model.Expense

class HomeViewModel(
    expenseDataSource: ExpenseDataSource
) : ViewModel() {
    private val _expenses = MutableStateFlow(emptyList<Expense>())
    val expenses = expenseDataSource.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )
}