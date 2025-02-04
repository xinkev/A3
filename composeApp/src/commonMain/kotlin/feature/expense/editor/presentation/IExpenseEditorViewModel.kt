package feature.expense.editor.presentation

import androidx.compose.foundation.text.input.TextFieldState
import com.xinkev.keypad.KeypadKey
import common.domain.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IExpenseEditorViewModel {
    val amount: TextFieldState
    val notes: TextFieldState
    val dateMillis: StateFlow<Long>
    val category: StateFlow<Category?>
    val enableAddButton: StateFlow<Boolean>

    fun onKeyPressed(key: KeypadKey)
    fun onDateChanged(dateMillis: Long)
    fun onCategoryChanged(category: Category?)
    fun onClickAdd()
}

object ExpenseEditorPreviewViewModel : IExpenseEditorViewModel {
    override val amount: TextFieldState
        get() = TextFieldState()
    override val notes: TextFieldState
        get() = TextFieldState()
    override val dateMillis: StateFlow<Long>
        get() = MutableStateFlow(0)
    override val category: StateFlow<Category?>
        get() = MutableStateFlow(null)
    override val enableAddButton: StateFlow<Boolean>
        get() = MutableStateFlow(false)

    override fun onKeyPressed(key: KeypadKey) {
    }

    override fun onDateChanged(dateMillis: Long) {
    }

    override fun onCategoryChanged(category: Category?) {
    }

    override fun onClickAdd() {
    }
}
