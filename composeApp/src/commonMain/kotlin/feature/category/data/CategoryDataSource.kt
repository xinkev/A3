package feature.category.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.xinkev.a3.sqldelight.A3Database
import core.Dispatchers
import feature.category.domain.model.CategoryIconName
import feature.category.mapper.mapSqlResultToCategory

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
        iconName: CategoryIconName,
    ) = queries.insert(
        name = name,
        icon = iconName.key
    )
}
