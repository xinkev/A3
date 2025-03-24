package feature.home.presentation

import feature.category.common.domain.model.Category
import feature.category.common.domain.model.CategoryIconName
import feature.expense.common.domain.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDateTime

interface IHomeViewModel {
    val expenses: StateFlow<List<Expense>>
    val dateMillis: StateFlow<Long>
    val currentDateTotal: StateFlow<Double>
    val currentMonthTotal: StateFlow<Double>
    fun setDate(dateMillis: Long)
    fun onClickAddExpense()
    fun onClickTransaction(expense: Expense)
    fun onDeleteConfirmed(expense: Expense)
}

internal data object PreviewHomeViewModel : IHomeViewModel {
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
    override val currentDateTotal: StateFlow<Double>
        get() = MutableStateFlow(0.0)
    override val currentMonthTotal: StateFlow<Double>
        get() = MutableStateFlow(0.0)

    override fun setDate(dateMillis: Long) {
    }

    override fun onClickAddExpense() {
    }

    override fun onClickTransaction(expense: Expense) {
    }

    override fun onDeleteConfirmed(expense: Expense) {
    }

    private fun previewExpense(detail: String, cost: Double): Expense {
        return Expense(
            uuid = "",
            category = Category(
                uuid = "12",
                name = "Bar",
                iconName = CategoryIconName.IPhone
            ),
            cost = cost,
            detail = detail,
            datetime = LocalDateTime(2024, 5, 5, 16, 27, 30),
        )
    }
}
