package core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import com.xinkev.a3.BuildConfig
import com.xinkev.a3.sqldelight.A3Database

actual object DriverFactory {
    actual fun create(): SqlDriver {
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
