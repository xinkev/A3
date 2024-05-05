package data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import core.Dispatchers
import database.DatabaseFactory
import kotlinx.coroutines.flow.Flow
import mapper.mapSqlResultToExpense
import model.Expense

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
}