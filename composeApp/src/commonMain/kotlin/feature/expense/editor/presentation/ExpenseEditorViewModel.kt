package feature.expense.editor.presentation

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.xinkev.keypad.KeypadState
import common.util.A3DateFormat
import common.util.dateTimeMilliToString
import common.util.toSmartString
import core.event.EventBus
import core.event.NavigationEvent
import feature.category.common.domain.model.Category
import feature.expense.common.data.ExpenseDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import navigation.Route.HomeGraph.ExpenseEditor
import navigation.types.expenseTypeMap

class ExpenseEditorViewModel(
    private val dataSource: ExpenseDataSource,
    private val eventBus: EventBus,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), IExpenseEditorViewModel {
    private val navArgs = savedStateHandle
        .toRoute<ExpenseEditor>(expenseTypeMap)

    private val selectedExpense = navArgs.expense
    override val keypadState = KeypadState(
        amount = TextFieldState(selectedExpense?.cost?.toSmartString() ?: ""),
        note = TextFieldState(selectedExpense?.detail ?: "")
    )
    override val isEdit: Boolean = selectedExpense != null
    private val _dateMillis = MutableStateFlow(
        navArgs.initialDate ?: Clock.System.now().toEpochMilliseconds()
    )

    override val dateMillis = _dateMillis.asStateFlow()
    private val _category = MutableStateFlow(selectedExpense?.category)
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
        if (isEdit) {
            dataSource.update(
                uuid = selectedExpense!!.uuid,
                amount = amountInDouble,
                notes = keypadState.note.text.toString(),
                dateTime = date,
                categoryId = _category.value!!.uuid
            )
            viewModelScope.launch {
                eventBus.send(NavigationEvent.NavigateUp)
            }
        } else {
            dataSource.insert(
                amount = amountInDouble,
                notes = keypadState.note.text.toString(),
                dateTime = date,
                categoryId = _category.value!!.uuid
            )
        }

        resetState()
    }

    private fun resetState() {
        keypadState.clear()
        _dateMillis.value = Clock.System.now().toEpochMilliseconds()
        _category.value = null
    }
}
