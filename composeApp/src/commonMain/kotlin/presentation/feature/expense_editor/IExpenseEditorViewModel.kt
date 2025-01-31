package presentation.feature.expense_editor

import kotlinx.coroutines.flow.StateFlow
import presentation.model.Category

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
