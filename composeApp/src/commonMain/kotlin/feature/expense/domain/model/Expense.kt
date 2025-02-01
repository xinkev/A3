package feature.expense.domain.model

import kotlinx.datetime.LocalDateTime

data class Expense(
    val uuid: String,
    val detail: String?,
    val datetime: LocalDateTime,
    val category: String,
    val cost: Double
)
