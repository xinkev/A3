package feature.category.common.mapper

import common.domain.model.A3Icon
import feature.category.categories.categoryIcons
import feature.category.common.domain.model.Category
import feature.category.common.domain.model.CategoryIconName

fun mapSqlResultToCategory(name: String, icon: String): Category {
    return Category(
        name, mapIcon(icon)
    )
}

private fun mapIcon(icon: String): A3Icon? {
    val type = CategoryIconName.entries.firstOrNull { it.key == icon } ?: return null
    return categoryIcons[type]
}
