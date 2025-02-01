package feature.expense.editor.presentation

import common.domain.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface IExpenseEditorViewModel {
    val amount: StateFlow<String>
    val notes: StateFlow<String>
    val dateMillis: StateFlow<Long>
    val category: StateFlow<Category?>
    val enableAddButton: StateFlow<Boolean>

    fun onAmountChanged(amount: String)
    fun onNotesChanged(notes: String)
    fun onDateChanged(dateMillis: Long)
    fun onCategoryChanged(category: Category?)
    fun onClickAdd()
}

object ExpenseEditorPreviewViewModel : IExpenseEditorViewModel {
    override val amount: StateFlow<String>
        get() = MutableStateFlow("")
    override val notes: StateFlow<String>
        get() = MutableStateFlow("")
    override val dateMillis: StateFlow<Long>
        get() = MutableStateFlow(0)
    override val category: StateFlow<Category?>
        get() = MutableStateFlow(null)
    override val enableAddButton: StateFlow<Boolean>
        get() = MutableStateFlow(false)

    override fun onAmountChanged(amount: String) {
    }

    override fun onNotesChanged(notes: String) {
    }

    override fun onDateChanged(dateMillis: Long) {
    }

    override fun onCategoryChanged(category: Category?) {
    }

    override fun onClickAdd() {
    }
}
