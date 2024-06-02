package data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import core.Dispatchers
import core.randomUUID
import database.DatabaseFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.TimeZone
import mapper.mapSqlResultToExpense
import presentation.model.Expense

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

    fun insert(
        amount: Double,
        notes: String,
        dateTime: String,
        timeZone: String = TimeZone.currentSystemDefault().id,
        category: String,
        uuid: String = randomUUID(),
    ) {
        db.transaction {
            queries.insert(
                cost = amount,
                detail = notes,
                datetime = dateTime,
                category = category,
                timezone = timeZone,
                uuid = uuid
            )
        }
    }
}