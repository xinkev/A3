package feature.expense.editor.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xinkev.keypad.KeypadKey
import com.xinkev.logger.log
import common.domain.model.Category
import common.util.A3DateFormat
import common.util.dateTimeMilliToString
import feature.expense.data.ExpenseDataSource
import feature.expense.domain.AmountInputHandler
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
    override val amount = TextFieldState()
    override val notes = TextFieldState()
    private val _dateMillis = MutableStateFlow(Clock.System.now().toEpochMilliseconds())
    override val dateMillis = _dateMillis.asStateFlow()
    private val _category = MutableStateFlow<Category?>(null)
    override val category = _category.asStateFlow()

    private val amountInputHandler = AmountInputHandler(
        coroutineScope = viewModelScope,
        state = amount,
    )
    override val enableAddButton: StateFlow<Boolean> = combine(
        amountInputHandler.stateFlow,
        category
    ) { amount, category ->
        amount.isNotBlank() && category != null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    override fun onDateChanged(dateMillis: Long) {
        _dateMillis.value = dateMillis
    }

    override fun onCategoryChanged(category: Category?) {
        _category.value = category
    }

    override fun onClickAdd() {
        // TODO: Handle it more graciously
        val amountInDouble = amount.text.toString().toDoubleOrNull() ?: return
        // convert dateMillis to date
        val date = dateTimeMilliToString(_dateMillis.value, A3DateFormat.ISO8601)
        dataSource.insert(
            amount = amountInDouble,
            notes = notes.text.toString(),
            dateTime = date,
            category = _category.value!!.name
        )

        resetState()
    }

    override fun onKeyPressed(key: KeypadKey) {
        log.i { key }
    }

    private fun resetState() {
        amount.clearText()
        notes.clearText()
        _dateMillis.value = Clock.System.now().toEpochMilliseconds()
        _category.value = null
    }
}
