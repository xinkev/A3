package feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.util.A3DateFormat
import common.util.dateTimeMilliToString
import common.util.now
import core.event.EventBus
import core.event.NavigationEvent.NavigateToExpenseEditor
import feature.expense.common.data.ExpenseDataSource
import feature.expense.common.domain.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val expenseDataSource: ExpenseDataSource,
    private val eventBus: EventBus
) : ViewModel(), IHomeViewModel {
    private val _dateMillis = MutableStateFlow(now.toEpochMilliseconds())
    override val dateMillis: StateFlow<Long>
        get() = _dateMillis.asStateFlow()

    override val expenses = _dateMillis.flatMapLatest {
        expenseDataSource.getByDateTime(dateTimeMilliToString(it, A3DateFormat.ISO8601))
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    override fun setDate(dateMillis: Long) {
        println(
            dateTimeMilliToString(dateMillis, A3DateFormat.DisplayDateTime)
        )
        _dateMillis.value = dateMillis
    }

    override fun onClickAddExpense() {
        viewModelScope.launch {
            eventBus.send(NavigateToExpenseEditor(initialDate = dateMillis.value))
        }
    }

    override fun onClickTransaction(expense: Expense) {
        viewModelScope.launch {
            eventBus.send(NavigateToExpenseEditor(expense))
        }
    }

    override fun onDeleteConfirmed(expense: Expense) {
        expenseDataSource.delete(expense.uuid)
    }
}
