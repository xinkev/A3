package feature.settings.backup.data

import com.xinkev.logger.log
import common.util.A3DateFormat
import common.util.dateTimeMilliToString
import common.util.localDateTimeToString
import core.Outcome
import core.file.FileManager
import feature.category.common.data.CategoryDataSource
import feature.expense.common.data.ExpenseDataSource
import feature.settings.backup.domain.adapter.DataExporter
import feature.settings.backup.domain.model.ExportError
import feature.settings.backup.domain.model.TaiyakiData
import feature.settings.backup.serilization.taiyakiJson
import io.github.vinceglb.filekit.core.PlatformDirectory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone

private val availableCategoryIcons = listOf(
    "tag",
    "food",
    "snack",
    "bus",
    "shopping",
    "tshirt",
    "iphone",
    "book",
    "gift",
    "credit-card",
    "house",
    "map",
    "medical",
    "mortar-board",
    "coffee",
    "office-chair",
    "stroller"
)

class ExpenseTaiyakiDataExporter(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val fileManager: FileManager,
    private val categoryDataSource: CategoryDataSource,
    private val expenseDataSource: ExpenseDataSource,
) : DataExporter {
    override val source: String = "taiyaki"
    override val format: String = "json"

    override suspend fun export(directory: PlatformDirectory): Outcome<ExportError, Unit> {
        return withContext(dispatcher) {
            try {
                val categories = categoryDataSource.getAllCategories()
                val expenses = expenseDataSource.getAll()
                val taiyakiData = TaiyakiData(
                    version = "1.1",
                    categories = categories.map { category ->
                        TaiyakiData.Category(
                            name = category.name,
                            icon = availableCategoryIcons.firstOrNull { it == category.iconName?.realName }
                                ?: availableCategoryIcons.first(),
                        )
                    },
                    expenses = expenses.map { expense ->
                        TaiyakiData.Expense(
                            uuid = expense.uuid,
                            detail = expense.detail ?: "",
                            datetime = localDateTimeToString(
                                expense.datetime,
                                A3DateFormat.DisplayDateTime
                            ),
                            category = expense.category.name,
                            cost = expense.cost.toString(),
                            timezone = TimeZone.currentSystemDefault().id,
                        )
                    }
                )

                val bytes = taiyakiJson.encodeToString(taiyakiData).encodeToByteArray()
                val timestamp = dateTimeMilliToString(
                    Clock.System.now().toEpochMilliseconds(),
                    A3DateFormat.FileName
                )
                val fileWriteOutCome = fileManager.writeFile(
                    data = bytes,
                    directory = directory,
                    fileName = "A3TaiyakiExport$timestamp.json"
                )
                return@withContext fileWriteOutCome
            } catch (e: Exception) {
                log.w(throwable = e) { "export failed" }
                return@withContext Outcome.Error(ExportError.Unknown)
            }
        }
    }
}
