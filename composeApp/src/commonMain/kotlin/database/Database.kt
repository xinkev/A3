package database

import com.xinkev.a3.sqldelight.A3Database

class Database(driverFactory: DriverFactory) {
    private val db = A3Database(driverFactory.create())
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