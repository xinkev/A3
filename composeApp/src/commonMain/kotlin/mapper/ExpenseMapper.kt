package mapper

import kotlinx.datetime.LocalDateTime
import presentation.model.Expense
import util.A3DateFormat

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
