package feature.category.mapper

import common.domain.model.A3Icon
import feature.category.presentation.categoryIcons
import feature.category.domain.model.Category
import feature.category.domain.model.CategoryIconName

fun mapSqlResultToCategory(name: String, icon: String): Category {
    return Category(
        name, mapIcon(icon)
    )
}

private fun mapIcon(icon: String): A3Icon? {
    val type = CategoryIconName.entries.firstOrNull { it.key == icon } ?: return null
    return categoryIcons[type]
}
