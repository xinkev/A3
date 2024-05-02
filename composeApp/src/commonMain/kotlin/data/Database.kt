package data

import app.cash.sqldelight.db.SqlDriver
import com.xinkev.a3.Database

interface DriverFactory {
    fun createDriver(): SqlDriver
}

class AppDatabase(driverFactory: DriverFactory) {
    private val db = Database(driverFactory.createDriver())
    private val queries = db.expenseQueries

    fun addExpense() {
        db.transaction {
            queries.insert(
                "test",
                timezone = "Asia/Tokyo",
                datetime = "2024-04-02",
                detail = "Egg",
                category = "Food",
                cost = 300.0
            )
        }
    }

    fun getExpenses(): String {
        return queries.selectAll().executeAsList().joinToString("\n")
    }
}