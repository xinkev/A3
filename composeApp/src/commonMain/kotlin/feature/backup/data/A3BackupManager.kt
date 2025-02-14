package feature.backup.data

import com.xinkev.a3.sqldelight.A3Database
import core.Outcome
import core.database.DatabaseFactory
import core.randomUUID
import feature.backup.domain.adapter.RestoreAdapter
import feature.backup.domain.models.Backup
import feature.backup.domain.models.BackupError

class A3BackupManager(
    databaseFactory: DatabaseFactory,
) {
    private val db: A3Database = databaseFactory.create()
    private val expenseQueries = db.expenseQueries
    private val categoryQueries = db.categoryQueries
    private lateinit var restoreAdapter: RestoreAdapter

    fun setAdapter(adapter: RestoreAdapter) {
        restoreAdapter = adapter
    }

    suspend fun restore(json: String): Outcome<BackupError, Unit> {
        val data = restoreAdapter.restore(json)
        db.transaction {
            writeCategories(data.categories)
            writeExpenses(data.expenses)
        }
        return Outcome.Success(Unit)
    }

    private fun writeExpenses(data: List<Backup.Expense>) {
        for (expense in data) {
            // find category
            val category = categoryQueries
                    .selectByName(expense.category)
                    .executeAsOneOrNull() ?: continue
            expenseQueries.insert(
                uuid = expense.uuid,
                categoryId = category.uuid,
                cost = expense.cost,
                datetime = expense.datetimeISO8601,
                detail = expense.detail
            )
        }
    }

    private fun writeCategories(data: List<Backup.Category>) {
        for (category in data) {
            categoryQueries.insert(
                uuid = randomUUID(),
                name = category.name,
                icon = category.icon
            )
        }
    }
}
