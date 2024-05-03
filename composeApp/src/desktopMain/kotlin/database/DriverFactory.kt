package database

import a_.composeApp.BuildConfig
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.xinkev.a3.sqldelight.A3Database
import java.io.File

class DesktopDriverFactory: DriverFactory {
    override fun create(): SqlDriver {
        val debug = BuildConfig.isDebug
        val parentFolder = if (debug) {
            File(System.getProperty("java.io.tmpdir"))
        } else {
            File(System.getProperty("user.home"), ".${BuildConfig.appName}")
        }

        if (!parentFolder.exists()) {
            parentFolder.mkdirs()
        }

        val dbPath = File(parentFolder, BuildConfig.dbName)
        println("Database path: ${dbPath.absolutePath}") // Print out the database path

        val driver: SqlDriver = JdbcSqliteDriver(url = "jdbc:sqlite:${dbPath.absolutePath}")

        if (!dbPath.exists()) {
            dbPath.createNewFile() // Create the database file if it doesn't exist
            A3Database.Schema.create(driver)
        }

        return driver
    }
}