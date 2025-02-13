package feature.category.common.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.xinkev.a3.sqldelight.A3Database
import core.Dispatchers
import feature.category.common.domain.model.Category
import feature.category.common.mapper.mapSqlResultToCategory

class CategoryDataSource(
    db: A3Database,
    private val dispatchers: Dispatchers
) {
    private val queries = db.categoryQueries

    fun getAllCategories() = queries.selectAll(mapper = ::mapSqlResultToCategory)
        .asFlow()
        .mapToList(dispatchers.io)

    fun addCategory(
        name: String,
        iconName: String,
    ) = queries.insert(
        name = name,
        icon = iconName
    )

    fun updateCategory(
        originalName: String,
        name: String,
        iconName: String,
    ) = queries.update(name, iconName, originalName)

    fun selectCategoryBy(name: String): Category? =
        queries.selectByName(name, ::mapSqlResultToCategory)
            .executeAsOneOrNull()
}
