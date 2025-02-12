package common.mapper

import common.util.A3DateFormat
import feature.expense.common.domain.model.Expense
import kotlinx.datetime.LocalDateTime

fun mapSqlResultToExpense(
    uuid: String,
    detail: String?,
    datetime: String,
    category: String,
    cost: Double,
): Expense {
    val parsedDateTime = LocalDateTime.parse(datetime, format = A3DateFormat.ISO8601.value)

    return Expense(
        uuid = uuid,
        category = category,
        detail = detail,
        datetime = parsedDateTime,
        cost = cost,
    )
}
