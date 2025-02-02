package feature.expense.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.xinkev.logger.log
import common.mapper.mapSqlResultToExpense
import core.Dispatchers
import core.randomUUID
import database.DatabaseFactory
import feature.expense.domain.model.Expense
import kotlinx.coroutines.flow.Flow

class ExpenseDataSource(
    dbFactory: DatabaseFactory,
    private val dispatchers: Dispatchers
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
        category: String,
        uuid: String = randomUUID(),
    ) {
        db.transaction {
            queries.insert(
                cost = amount,
                detail = notes,
                datetime = dateTime,
                category = category,
                uuid = uuid
            )
        }
    }
}
