package feature.settings.backup

import feature.settings.backup.domain.model.TaiyakiData
import feature.settings.backup.domain.model.TaiyakiData.Category
import feature.settings.backup.domain.model.TaiyakiData.Expense
import feature.settings.backup.serilization.taiyakiJson
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TaiyakiDataSerializationTest {
    @Test
    fun restore_validJson_returnsBackupObject() = runTest {
        // Arrange
        val json = """
            {
                "version": "1.0.0",
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
                        "cost": "10.0",
                         "timezone": "Asia/Tokyo"
                    }
                ]
            }
        """
        val expected = TaiyakiData(
            version = "1.0.0", categories = listOf(
                Category(name = "Groceries", icon = "cart"),
                Category(name = "Eating Out", icon = "restaurant")
            ), expenses = listOf(
                Expense(
                    uuid = "12345",
                    detail = "Groceries",
                    datetime = "2023-01-01 00:00:00",
                    category = "Groceries",
                    cost = "10.0",
                    timezone = "Asia/Tokyo"
                )
            )
        )
        // Act
        val result = taiyakiJson.decodeFromString<TaiyakiData>(json)
        // Assert
        assertEquals(result, expected)
    }

    @Test
    fun restore_invalidJson_throwsSerializationException() = runTest {
        // Arrange
        val emptyJson = ""

        // Act & Assert
        assertFailsWith(SerializationException::class) {
            taiyakiJson.decodeFromString<TaiyakiData>(emptyJson)
        }
    }

    @Test
    fun restore_extraFieldsInJson_ignoresExtraFields() = runTest {
        // Arrange
        val json = """
            {
                "version": "1.0.0",
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
                        "cost": "10.0",
                        "timezone": "Asia/Tokyo"
                    }
                ],
                "extraField": "extraValue"
            }
        """
        val expected = TaiyakiData(
            version = "1.0.0",
            categories = listOf(
                Category(name = "Groceries", icon = "cart")
            ),
            expenses = listOf(
                Expense(
                    uuid = "12345",
                    detail = "Groceries",
                    datetime = "2023-01-01 00:00:00",
                    category = "Groceries",
                    cost = "10.0",
                    timezone = "Asia/Tokyo"
                )
            )
        )
        // Act
        val result = taiyakiJson.decodeFromString<TaiyakiData>(json)
        // Assert
        assertEquals(result, expected)
    }

    @Test
    fun restore_incorrectDataTypeInJson_throwsSerializationException() = runTest {
        // Arrange
        val json = """
            {
                "version": 1,
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
                        "cost": "ten dollars",
                         "timezone": "Asia/Tokyo",
                    }
                ]
            }
        """
        // Act & Assert
        assertFailsWith(SerializationException::class) {
            taiyakiJson.decodeFromString<TaiyakiData>(json)
        }
    }

    @Test
    fun restore_emptyExpensesList_returnsBackupWithEmptyExpenses() = runTest {
        // Arrange
        val json = """
            {
                "version": "1.0.0",
                "categories": [
                    {
                        "name": "Groceries",
                        "icon": "cart"
                    }
                ],
                "expenses": []
            }
        """
        val expected = TaiyakiData(
            version = "1.0.0",
            categories = listOf(
                Category(name = "Groceries", icon = "cart")
            ),
            expenses = emptyList()
        )
        // Act
        val result = taiyakiJson.decodeFromString<TaiyakiData>(json)
        // Assert
        assertEquals(result, expected)
    }

    @Test
    fun restore_emptyCategoriesAndExpenses_returnsBackupWithEmptyLists() = runTest {
        // Arrange
        val json = """
        {
            "version": "1.0.0",
            "categories": [],
            "expenses": []
        }
    """
        val expected = TaiyakiData(
            version = "1.0.0",
            categories = emptyList(),
            expenses = emptyList()
        )
        // Act
        val result = taiyakiJson.decodeFromString<TaiyakiData>(json)
        // Assert
        assertEquals(result, expected)
    }

    @Test
    fun restore_missingVersion_returnsBackupWithNullVersion() = runTest {
        // Arrange
        val json = """
        {
            "categories": [],
            "expenses": []
        }
    """
        val expected = TaiyakiData(
            version = null, // version is missing in JSON, so it should be null
            categories = emptyList(),
            expenses = emptyList()
        )
        // Act
        val result = taiyakiJson.decodeFromString<TaiyakiData>(json)
        // Assert
        assertEquals(result, expected)
    }

}
