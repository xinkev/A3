package feature.expense.editor.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xinkev.keypad.KeypadState
import common.util.A3DateFormat
import common.util.dateTimeMilliToString
import feature.category.common.domain.model.Category
import feature.expense.common.data.ExpenseDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock

class ExpenseEditorViewModel(
    private val dataSource: ExpenseDataSource
) : ViewModel(), IExpenseEditorViewModel {
    override val keypadState = KeypadState()

    private val _dateMillis = MutableStateFlow(Clock.System.now().toEpochMilliseconds())
    override val dateMillis = _dateMillis.asStateFlow()
    private val _category = MutableStateFlow<Category?>(null)
    override val category = _category.asStateFlow()
    override val enableAddButton: StateFlow<Boolean> = combine(
        keypadState.amountAsFlow(),
        category
    ) { amount, category ->
        amount.isNotBlank() && category != null && keypadState.isValid
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    override fun onDateChanged(dateMillis: Long) {
        _dateMillis.value = dateMillis
    }

    override fun onCategoryChanged(category: Category?) {
        _category.value = category
    }

    override fun onClickAdd() {
        // TODO: Handle it more graciously
        val amountInDouble = keypadState.amount.text.toString().toDoubleOrNull() ?: return
        // convert dateMillis to date
        val date = dateTimeMilliToString(_dateMillis.value, A3DateFormat.ISO8601)
        dataSource.insert(
            amount = amountInDouble,
            notes = keypadState.note.text.toString(),
            dateTime = date,
            category = _category.value!!.name
        )

        resetState()
    }

    private fun resetState() {
        keypadState.clear()
        _dateMillis.value = Clock.System.now().toEpochMilliseconds()
        _category.value = null
    }
}
