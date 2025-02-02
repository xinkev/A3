package feature.backup.data

import feature.backup.domain.models.Backup
import feature.backup.domain.models.Backup.Category
import feature.backup.domain.models.Backup.Expense
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerializationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ExpenseTaiyakiRestoreAdapterTest {
    private val adapter = ExpenseTaiyakiRestoreAdapter()

    @Test
    fun restore_validJson_returnsBackupObject() = runTest {
        // Arrange
        val json = """
            {
                "appVersion": "1.0.0",
                "categories": [
                    {
                        "name": "Groceries",
                        "icon": "cart"
                    },
                    {
                        "name": "Eating Out",
                        "icon": "restaurant"
                    }
                ],
                "expenses": [
                    {
                        "uuid": "12345",
                        "detail": "Groceries",
                        "datetime": "2023-01-01 00:00:00",
                        "category": "Groceries",
                        "cost": 10.0
                    }
                ]
            }
        """
        val expected = Backup(
            appVersion = "1.0.0", categories = listOf(
                Category(name = "Groceries", icon = "cart"),
                Category(name = "Eating Out", icon = "restaurant")
            ), expenses = listOf(
                Expense(
                    uuid = "12345", detail = "Groceries", datetime = LocalDateTime(
                        year = 2023, monthNumber = 1, dayOfMonth = 1, hour = 0, minute = 0
                    ), category = "Groceries", cost = 10.0
                )
            )
        )
        // Act
        val result = adapter.restore(json)
        // Assert
        assertEquals(result, expected)
    }

    @Test
    fun restore_invalidJson_throwsSerializationException() = runTest {
        // Arrange
        val emptyJson = ""

        // Act & Assert
        assertFailsWith(SerializationException::class) {
            adapter.restore(emptyJson)
        }
    }

    @Test
    fun restore_extraFieldsInJson_ignoresExtraFields() = runTest {
        // Arrange
        val json = """
            {
                "appVersion": "1.0.0",
                "categories": [
                    {
                        "name": "Groceries",
                        "icon": "cart"
                    }
                ],
                "expenses": [
                    {
                        "uuid": "12345",
                        "detail": "Groceries",
                        "datetime": "2023-01-01 00:00:00",
                        "category": "Groceries",
                        "cost": 10.0
                    }
                ],
                "extraField": "extraValue"
            }
        """
        val expected = Backup(
            appVersion = "1.0.0",
            categories = listOf(
                Category(name = "Groceries", icon = "cart")
            ),
            expenses = listOf(
                Expense(
                    uuid = "12345", detail = "Groceries", datetime = LocalDateTime(
                        year = 2023, monthNumber = 1, dayOfMonth = 1, hour = 0, minute = 0
                    ), category = "Groceries", cost = 10.0
                )
            )
        )
        // Act
        val result = adapter.restore(json)
        // Assert
        assertEquals(result, expected)
    }

    @Test
    fun restore_incorrectDataTypeInJson_throwsSerializationException() = runTest {
        // Arrange
        val json = """
            {
                "appVersion": 1,
                "categories": [
                    {
                        "name": "Groceries",
                        "icon": "cart"
                    }
                ],
                "expenses": [
                    {
                        "uuid": "12345",
                        "detail": "Groceries",
                        "datetime": "2023-01-01 00:00:00",
                        "category": "Groceries",
                        "cost": "ten dollars"
                    }
                ]
            }
        """
        // Act & Assert
        assertFailsWith(SerializationException::class) {
            adapter.restore(json)
        }
    }

    @Test
    fun restore_emptyExpensesList_returnsBackupWithEmptyExpenses() = runTest {
        // Arrange
        val json = """
            {
                "appVersion": "1.0.0",
                "categories": [
                    {
                        "name": "Groceries",
                        "icon": "cart"
                    }
                ],
                "expenses": []
            }
        """
        val expected = Backup(
            appVersion = "1.0.0",
            categories = listOf(
                Category(name = "Groceries", icon = "cart")
            ),
            expenses = emptyList()
        )
        // Act
        val result = adapter.restore(json)
        // Assert
        assertEquals(result, expected)
    }

    @Test
    fun restore_emptyCategoriesAndExpenses_returnsBackupWithEmptyLists() = runTest {
        // Arrange
        val json = """
        {
            "appVersion": "1.0.0",
            "categories": [],
            "expenses": []
        }
    """
        val expected = Backup(
            appVersion = "1.0.0",
            categories = emptyList(),
            expenses = emptyList()
        )
        // Act
        val result = adapter.restore(json)
        // Assert
        assertEquals(result, expected)
    }

    @Test
    fun restore_missingAppVersion_returnsBackupWithNullAppVersion() = runTest {
        // Arrange
        val json = """
        {
            "categories": [],
            "expenses": []
        }
    """
        val expected = Backup(
            appVersion = null, // appVersion is missing in JSON, so it should be null
            categories = emptyList(),
            expenses = emptyList()
        )
        // Act
        val result = adapter.restore(json)
        // Assert
        assertEquals(result, expected)
    }

}
