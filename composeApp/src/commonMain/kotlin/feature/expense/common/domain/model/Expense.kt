package feature.expense.common.domain.model

import feature.category.common.domain.model.Category
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Expense(
    val uuid: String,
    val detail: String?,
    val datetime: LocalDateTime,
    val category: Category,
    val cost: Double
)
