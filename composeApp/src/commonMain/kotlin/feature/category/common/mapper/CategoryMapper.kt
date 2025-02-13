package feature.category.common.mapper

import feature.category.common.domain.model.Category
import feature.category.common.domain.model.CategoryIconName

fun mapSqlResultToCategory(name: String, icon: String): Category {
    return Category(name, mapIcon(icon))
}

private fun mapIcon(icon: String): CategoryIconName? {
    return CategoryIconName.entries.firstOrNull { it.key == icon }
}
