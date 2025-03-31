package feature.expense.common.domain.model

import common.domain.model.IconName

data class TotalExpensePerCategory(
    val category: String,
    val categoryIcon: IconName?,
    val totalAmount: Double,
)
