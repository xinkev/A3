package feature.category.common.mapper

import feature.category.common.domain.model.Category
import feature.category.common.domain.model.CategoryIconName

fun mapSqlResultToCategory(uuid: String, name: String, icon: String): Category {
    return Category(uuid, name, mapCategoryIcon(icon))
}

fun mapCategoryIcon(icon: String): CategoryIconName? {
    return CategoryIconName.entries.firstOrNull { it.realName == icon }
}
