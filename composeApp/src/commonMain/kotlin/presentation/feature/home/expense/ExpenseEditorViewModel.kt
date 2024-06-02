package presentation.feature.home.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.ExpenseDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import presentation.model.Category

class ExpenseEditorViewModel(
    private val dataSource: ExpenseDataSource
) : ViewModel(), IExpenseEditorViewModel {
    private val _amount = MutableStateFlow("")
    override val amount = _amount.asStateFlow()

    private val _notes = MutableStateFlow("")
    override val notes = _notes.asStateFlow()

    private val _dateMillis = MutableStateFlow(Clock.System.now().toEpochMilliseconds())
    override val dateMillis = _dateMillis.asStateFlow()

    private val _category = MutableStateFlow<Category?>(null)
    override val category = _category.asStateFlow()

    override val enableAddButton: StateFlow<Boolean> = combine(
        amount,
        category
    ) { amount, category ->
        amount.isNotBlank() && category != null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)


    override fun onAmountChanged(amount: String) {
        _amount.value = amount
    }

    override fun onNotesChanged(notes: String) {
        _notes.value = notes
    }

    override fun onDateChanged(dateMillis: Long) {
        _dateMillis.value = dateMillis
    }

    override fun onCategoryChanged(category: Category?) {
        _category.value = category
    }

    override fun onClickAdd() {
        // convert dateMillis to date
        val instant = Instant.fromEpochMilliseconds(_dateMillis.value)
        val date = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        dataSource.insert(
            amount = _amount.value.toDouble(),
            notes = _notes.value,
            dateTime = date.toString(),
            category = _category.value!!.name
        )

        resetState()
    }

    private fun resetState() {
        _amount.value = ""
        _notes.value = ""
        _dateMillis.value = Clock.System.now().toEpochMilliseconds()
        _category.value = null
    }
}