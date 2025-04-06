package core.database

import app.cash.sqldelight.db.SqlDriver

expect object DriverFactory {
    fun create(): SqlDriver
}
