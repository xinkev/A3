package feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.util.A3DateFormat
import common.util.A3DateFormat.ISO8601
import common.util.dateTimeMilliToString
import common.util.now
import core.event.EventBus
import core.event.NavigationEvent.NavigateToExpenseEditor
import feature.expense.common.data.ExpenseDataSource
import feature.expense.common.domain.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val expenseDataSource: ExpenseDataSource,
    private val eventBus: EventBus
) : ViewModel(), IHomeViewModel {
    private val _dateMillis = MutableStateFlow(now.toEpochMilliseconds())
    override val dateMillis: StateFlow<Long>
        get() = _dateMillis.asStateFlow()
    private val currentDateTimeStringFlow: Flow<String>
        get() = _dateMillis.mapLatest {
            dateTimeMilliToString(it, ISO8601)
        }

    override val expenses = currentDateTimeStringFlow.flatMapLatest {
        expenseDataSource.getByDateTime(it)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    override val currentMonthTotal: StateFlow<Double>
        get() = _dateMillis
            .mapLatest { milli ->
                // Extract the year and month from the current date
                val currentMonth = dateTimeMilliToString(milli, ISO8601)
                currentMonth
            }
            .distinctUntilChanged() // Trigger only when the month changes
            .flatMapLatest { monthString ->
                expenseDataSource.getTotalExpense(
                    dateTime = monthString,
                    monthly = true
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(3_000),
                initialValue = 0.0
            )

    override val currentDateTotal: StateFlow<Double>
        get() = currentDateTimeStringFlow.flatMapLatest {
            expenseDataSource.getTotalExpense(
                dateTimeMilliToString(_dateMillis.value, ISO8601),
                monthly = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3_000),
            initialValue = 0.0
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

    override fun onClickExpense(expense: Expense) {
        viewModelScope.launch {
            eventBus.send(NavigateToExpenseEditor(expense))
        }
    }

    override fun onDeleteConfirmed(expense: Expense) {
        expenseDataSource.delete(expense.uuid)
    }
}
