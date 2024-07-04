package backup

import com.xinkev.a3.sqldelight.A3Database
import core.Outcome
import database.DatabaseFactory

class A3BackupManager(
    databaseFactory: DatabaseFactory,
) {
    private val db: A3Database = databaseFactory.create()
    private val expenseQueries = db.expenseQueries
    private val categoryQueries = db.categoryQueries
    private lateinit var importAdapter: ImportAdapter

    fun setAdapter(adapter: ImportAdapter) {
        importAdapter = adapter
    }

    suspend fun restore(json: String): Outcome<BackupError, Unit> {
        val data = importAdapter.import(json)
        db.transaction {
            writeExpenses(data.expenses)
            writeCategories(data.categories)
        }
        return Outcome.Success(Unit)
    }

    private fun writeExpenses(data: List<Backup.Expense>) {
        for (expense in data) {
            expenseQueries.insert(
                uuid = expense.uuid,
                category = expense.category,
                cost = expense.cost,
                datetime = expense.datetimeISO8601,
                detail = expense.detail
            )
        }
    }

    private fun writeCategories(data: List<Backup.Category>) {
        for (category in data) {
            categoryQueries.insert(
                name = category.name,
                icon = category.icon
            )
        }
    }
}