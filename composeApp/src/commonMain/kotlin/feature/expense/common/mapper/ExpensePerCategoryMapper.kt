package feature.expense.common.mapper

import common.mapper.stringToIconName
import feature.expense.common.domain.model.TotalExpensePerCategory

fun totalExpensePerCategoryMapper(
    category: String,
    categoryIcon: String,
    totalAmount: Double?,
): TotalExpensePerCategory = TotalExpensePerCategory(
    category = category,
    categoryIcon = stringToIconName(categoryIcon),
    totalAmount = totalAmount ?: 0.0,
)
