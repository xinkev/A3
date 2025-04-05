package feature.settings.backup.importing.data

import com.xinkev.a3.sqldelight.A3Database
import com.xinkev.logger.log
import common.util.A3DateFormat
import common.util.localDateTimeToString
import common.util.parseDateTime
import core.Outcome
import core.database.DatabaseFactory
import feature.category.common.data.CategoryDataSource
import feature.category.common.domain.model.Category
import feature.expense.common.data.ExpenseDataSource
import feature.settings.backup.common.serilization.taiyakiJson
import feature.settings.backup.importing.domain.adapter.DataImporter
import feature.settings.backup.importing.domain.model.ImportError
import feature.settings.backup.common.domain.model.TaiyakiData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.serialization.SerializationException

/**
 * Expense(Taiyaki) is an iOS-only app (not affiliated with us).
 * This adapter is used to restore backups from that app.
 */
class ExpenseTaiyakiDataImporter(
    databaseFactory: DatabaseFactory,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val categoryDataSource: CategoryDataSource,
    private val expenseDataSource: ExpenseDataSource,
) : DataImporter {
    private val db: A3Database = databaseFactory.create()
    override val format: String = "json"
    override val source: String = "taiyaki"

    override suspend fun import(readData: suspend () -> ByteArray): Outcome<ImportError, Unit> {
        return withContext(dispatcher) {
            try {
                val dataJsonString = readData().decodeToString()
                val data = taiyakiJson.decodeFromString<TaiyakiData>(dataJsonString)
                db.transaction {
                    categoryDataSource.addList(data.categories)
                    expenseDataSource.insertList(
                        data = data.expenses,
                        findCategory = { categoryDataSource.selectCategoryBy(it) }
                    )
                }
                Outcome.Success(Unit)
            } catch (e: SerializationException) {
                log.w(throwable = e) { "import failed" }
                Outcome.Error(ImportError.UnrecognizedFile)
            } catch (e: IllegalArgumentException) {
                log.w(throwable = e) { "import failed" }
                Outcome.Error(ImportError.UnrecognizedFile)
            } catch (e: Throwable) {
                log.w(throwable = e) { "import failed" }
                @Suppress("UNRESOLVED_REFERENCE")
                when {
                    // "Unresolved reference: OutOfMemoryError" occurs maybe because it's not available in Kotlin Native.
                    // Suppressing the error for now.
                    // If there's any issue, uncomment the line below and remove this condition.
                    e is OutOfMemoryError -> Outcome.Error(ImportError.FileTooLarge)
//                    e.message?.contains("File .+ is too big".toRegex()) == true -> Outcome.Error(BackupError.FileTooLarge(file))
                    else -> Outcome.Error(ImportError.FileAccessError)
                }
            }
        }
    }
}
private fun ExpenseDataSource.insertList(
    data: List<TaiyakiData.Expense>,
    findCategory: (name: String) -> Category?
) {
    for (expense in data) {
        val category = findCategory(expense.category)
        if (category != null) {
            val dateTime = parseDateTime(
                expense.datetime,
                timeZone = TimeZone.of(expense.timezone)
            )
            this.insert(
                uuid = expense.uuid,
                categoryId = category.uuid,
                amount = expense.cost.toDouble(),
                dateTime = localDateTimeToString(dateTime, A3DateFormat.ISO8601),
                notes = expense.detail
            )
        }
    }
}
