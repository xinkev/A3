package feature.expense.common.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.xinkev.logger.log
import common.mapper.mapSqlResultToExpense
import core.Dispatchers
import core.database.DatabaseFactory
import core.randomUUID
import feature.category.common.domain.model.Category
import feature.expense.common.domain.model.Expense
import feature.settings.backup.domain.model.TaiyakiData
import kotlinx.coroutines.flow.Flow

class ExpenseDataSource(
    dbFactory: DatabaseFactory,
    private val dispatchers: Dispatchers,
) {
    private val db = dbFactory.create()
    private val queries = db.expenseQueries

    fun getAll(): Flow<List<Expense>> {
        return queries.selectAll(mapper = ::mapSqlResultToExpense)
            .asFlow()
            .mapToList(dispatchers.io)
    }

    fun getByDateTime(
        dateTime: String,
    ): Flow<List<Expense>> {
        log.i { "getByDateTime: $dateTime" }
        return queries.selectByDateTime(
            value = dateTime,
            mapper = ::mapSqlResultToExpense
        )
            .asFlow()
            .mapToList(dispatchers.io)
    }

    fun insert(
        amount: Double,
        notes: String,
        dateTime: String,
        categoryId: String,
        uuid: String = randomUUID(),
    ) {
        db.transaction {
            queries.insert(
                cost = amount,
                detail = notes,
                datetime = dateTime,
                categoryId = categoryId,
                uuid = uuid
            )
        }
    }

    fun update(
        uuid: String,
        amount: Double,
        notes: String,
        dateTime: String,
        categoryId: String
    ) {
        queries.update(
            uuid = uuid,
            cost = amount,
            datetime = dateTime,
            detail = notes,
            categoryId = categoryId
        )
    }

    fun insertList(
        data: List<TaiyakiData.Expense>,
        findCategory: (name: String) -> Category?
    ) {
        for (expense in data) {
            val category = findCategory(expense.category)
            if (category != null) {
                insert(
                    uuid = expense.uuid,
                    categoryId = category.uuid,
                    amount = expense.cost,
                    dateTime = expense.datetimeISO8601,
                    notes = expense.detail
                )
            }
        }
    }

}
