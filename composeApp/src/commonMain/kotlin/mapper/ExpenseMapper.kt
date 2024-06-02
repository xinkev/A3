package mapper

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import presentation.model.Expense

fun mapSqlResultToExpense(
    uuid: String,
    timezone: String,
    detail: String?,
    datetime: String,
    category: String,
    cost: Double,
): Expense {
    val parsedDateTime = LocalDateTime.parse(datetime)

    return Expense(
        uuid = uuid,
        category = category,
        detail = detail,
        datetime = parsedDateTime,
        cost = cost,
        timezone = TimeZone.of(timezone)
    )
}
