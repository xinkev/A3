package database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.xinkev.a3.sqldelight.A3Database

class DesktopDriverFactory: DriverFactory {
    override fun create(): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        A3Database.Schema.create(driver)
        return driver
    }
}