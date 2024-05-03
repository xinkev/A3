package database

import app.cash.sqldelight.db.SqlDriver

interface DriverFactory {
    fun create(): SqlDriver
}