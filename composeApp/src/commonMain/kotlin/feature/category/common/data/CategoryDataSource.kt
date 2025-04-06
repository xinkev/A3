package feature.category.common.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import core.Dispatchers
import core.database.DatabaseFactory
import core.randomUUID
import feature.category.common.domain.model.Category
import feature.category.common.mapper.mapSqlResultToCategory
import feature.settings.backup.common.domain.model.TaiyakiData

class CategoryDataSource(
    databaseFactory: DatabaseFactory,
    private val dispatchers: Dispatchers
) {
    private val db = databaseFactory.create()
    private val queries = db.categoryQueries

    fun getAllCategoriesAsFlow() = queries.selectAll(mapper = ::mapSqlResultToCategory)
        .asFlow()
        .mapToList(dispatchers.io)

    fun getAllCategories() = queries.selectAll(mapper = ::mapSqlResultToCategory)
        .executeAsList()

    fun addCategory(
        name: String,
        iconName: String,
        uuid: String = randomUUID(),
    ) = queries.insert(
        uuid = uuid,
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

    fun addList(data: List<TaiyakiData.Category>) {
        for (category in data) {
            addCategory(
                uuid = randomUUID(),
                name = category.name,
                iconName = category.icon,
            )
        }
    }

    fun delete(uuid: String) {
        queries.delete(uuid)
    }
}
