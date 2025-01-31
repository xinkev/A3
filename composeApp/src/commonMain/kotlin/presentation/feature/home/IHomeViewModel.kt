package presentation.feature.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDateTime
import presentation.model.Category
import presentation.model.Expense

interface IHomeViewModel {
    val categories: StateFlow<List<Category>>
    val expenses: StateFlow<List<Expense>>
    val dateMillis: StateFlow<Long>
    fun setDate(dateMillis: Long)
}

internal data object PreviewHomeViewModel : IHomeViewModel {
    override val categories: StateFlow<List<Category>>
        get() = MutableStateFlow(emptyList())
    override val expenses: StateFlow<List<Expense>>
        get() = MutableStateFlow(
            listOf(
                previewExpense(detail = "Gift", cost = 62.33),
                previewExpense(detail = "GitHub Payment", cost = 7.00),
                previewExpense(detail = "Inboard", cost = 19.99),
                previewExpense(detail = "Chipotle", cost = 9.50)
            )
        )

    override val dateMillis: StateFlow<Long>
        get() = MutableStateFlow(0L)

    override fun setDate(dateMillis: Long) {
    }

    private fun previewExpense(detail: String, cost: Double): Expense {
        return Expense(
            uuid = "",
            category = "",
            cost = cost,
            detail = detail,
            datetime = LocalDateTime(2024, 5, 5, 16, 27, 30),
        )
    }
}
