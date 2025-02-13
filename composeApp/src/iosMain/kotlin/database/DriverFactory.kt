package database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import com.xinkev.a3.BuildConfig
import com.xinkev.a3.sqldelight.A3Database
import core.database.DriverFactory

class IOSDriverFactory : DriverFactory {
    override fun create(): SqlDriver {
        return NativeSqliteDriver(
            schema = A3Database.Schema,
            name = BuildConfig.dbName,
            onConfiguration = { config ->
                config.copy(
                    extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
                )
            }
        )
    }
}
