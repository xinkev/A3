package core.database

import app.cash.sqldelight.db.SqlDriver

interface DriverFactory {
    fun create(): SqlDriver
}
