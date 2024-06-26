package presentation.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone

data class Expense(
    val uuid: String,
    val detail: String?,
    val datetime: LocalDateTime,
    val category: String,
    val cost: Double
)