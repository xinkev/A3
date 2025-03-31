package feature.category.common.mapper

import common.mapper.stringToIconName
import feature.category.common.domain.model.Category

fun mapSqlResultToCategory(uuid: String, name: String, icon: String): Category {
    return Category(uuid, name, stringToIconName(icon))
}
