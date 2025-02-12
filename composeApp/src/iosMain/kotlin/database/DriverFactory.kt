package database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.xinkev.a3.BuildConfig
import com.xinkev.a3.sqldelight.A3Database
import core.database.DriverFactory

class IOSDriverFactory : DriverFactory {
    override fun create(): SqlDriver {
        return NativeSqliteDriver(A3Database.Schema, BuildConfig.dbName)
    }
}
