package feature.expense.expenseEditor

import com.xinkev.keypad.KeypadState
import feature.category.common.domain.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IExpenseEditorViewModel {
    val keypadState: KeypadState
    val dateMillis: StateFlow<Long>
    val category: StateFlow<Category?>
    val enableAddButton: StateFlow<Boolean>
    val isEdit: Boolean

    fun onDateChanged(dateMillis: Long)
    fun onCategoryChanged(category: Category?)
    fun onClickAdd()
}

object ExpenseEditorPreviewViewModel : IExpenseEditorViewModel {
    override val dateMillis: StateFlow<Long>
        get() = MutableStateFlow(0)
    override val category: StateFlow<Category?>
        get() = MutableStateFlow(null)
    override val enableAddButton: StateFlow<Boolean>
        get() = MutableStateFlow(false)
    override val keypadState: KeypadState
        get() = KeypadState()
    override val isEdit: Boolean
        get() = false

    override fun onDateChanged(dateMillis: Long) {
    }

    override fun onCategoryChanged(category: Category?) {
    }

    override fun onClickAdd() {
    }
}
