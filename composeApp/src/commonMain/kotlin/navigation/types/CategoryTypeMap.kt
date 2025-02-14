package navigation.types

import feature.category.common.domain.model.Category
import navigation.navType
import kotlin.reflect.typeOf

val categoryNavTypeMap = mapOf(typeOf<Category?>() to navType<Category?>())
