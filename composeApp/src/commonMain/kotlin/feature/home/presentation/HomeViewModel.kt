package feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xinkev.logger.log
import feature.category.common.data.CategoryDataSource
import common.util.A3DateFormat
import common.util.dateTimeMilliToString
import common.util.now
import core.event.EventBus
import core.event.NavigationEvent
import feature.expense.common.data.ExpenseDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    expenseDataSource: ExpenseDataSource,
    categoryDataSource: CategoryDataSource,
    private val eventBus: EventBus
) : ViewModel(), IHomeViewModel {
    private val _dateMillis = MutableStateFlow(now.toEpochMilliseconds())
    override val dateMillis: StateFlow<Long>
        get() = _dateMillis.asStateFlow()

    override val expenses = _dateMillis.flatMapLatest {
        log.i { "dateMillis: $it" }
        expenseDataSource.getByDateTime(dateTimeMilliToString(it, A3DateFormat.ISO8601))
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    override val categories = categoryDataSource.getAllCategories().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    override fun setDate(dateMillis: Long) {
        _dateMillis.value = dateMillis
    }

    override fun onClickAddExpense() {
        viewModelScope.launch {
            eventBus.send(NavigationEvent.NavigateToExpenseEditor)
        }
    }
}
