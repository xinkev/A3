package navigation.types

import feature.expense.common.domain.model.Expense
import navigation.navType
import kotlin.reflect.typeOf

val expenseTypeMap = mapOf(
    typeOf<Expense?>() to navType<Expense?>()
)
