package common.mapper

import common.util.A3DateFormat
import feature.category.common.domain.model.Category
import feature.category.common.mapper.mapCategoryIcon
import feature.expense.common.domain.model.Expense
import kotlinx.datetime.LocalDateTime

fun mapSqlResultToExpense(
    uuid: String,
    detail: String?,
    datetime: String,
    categoryId: String,
    cost: Double,
    categoryName: String,
    categoryIcon: String,
): Expense {
    val parsedDateTime = LocalDateTime.parse(datetime, format = A3DateFormat.ISO8601.value)

    return Expense(
        uuid = uuid,
        category = Category(
            uuid = categoryId,
            name = categoryName,
            iconName = mapCategoryIcon(categoryIcon),
        ),
        detail = detail,
        datetime = parsedDateTime,
        cost = cost,
    )
}
