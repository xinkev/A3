package presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.CategoryDataSource
import data.ExpenseDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import util.A3DateFormat
import util.dateTimeMilliToString
import util.log
import util.now

class HomeViewModel(
    expenseDataSource: ExpenseDataSource,
    categoryDataSource: CategoryDataSource,
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
}