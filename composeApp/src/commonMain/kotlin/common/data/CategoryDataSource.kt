package common.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.xinkev.a3.sqldelight.A3Database
import common.mapper.mapSqlResultToCategory
import core.Dispatchers

class CategoryDataSource(
    db: A3Database,
    private val dispatchers: Dispatchers
) {
    private val queries = db.categoryQueries

    fun getAllCategories() = queries.selectAll(mapper = ::mapSqlResultToCategory)
        .asFlow()
        .mapToList(dispatchers.io)
}
