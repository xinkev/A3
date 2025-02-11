package database

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.xinkev.a3.BuildConfig
import com.xinkev.a3.sqldelight.A3Database
import core.database.DriverFactory

class AndroidDriverFactory(private val context: Context): DriverFactory {
    override fun create(): SqlDriver = AndroidSqliteDriver(
        A3Database.Schema,
        context,
        BuildConfig.dbName,
        callback = object : AndroidSqliteDriver.Callback(A3Database.Schema) {
            override fun onOpen(db: SupportSQLiteDatabase) {
                db.execSQL("PRAGMA foreign_keys = ON;")
            }
        }
    )
}
