package core.database

import app.cash.sqldelight.db.SqlDriver
import com.xinkev.a3.sqldelight.A3Database

class DatabaseFactory(
    private val driver: SqlDriver = DriverFactory.create()
) {
    fun create(): A3Database = A3Database(driver)
}

