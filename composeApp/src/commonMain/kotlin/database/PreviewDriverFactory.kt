package database

import app.cash.sqldelight.Query
import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlPreparedStatement
import com.xinkev.a3.sqldelight.A3Database

internal val previewDatabaseFactory = object : DatabaseFactory {
    override fun create(): A3Database {
        return A3Database(object : SqlDriver {
            override fun close() {
                TODO("Not yet implemented")
            }

            override fun addListener(vararg queryKeys: String, listener: Query.Listener) {
                TODO("Not yet implemented")
            }

            override fun currentTransaction(): Transacter.Transaction? {
                TODO("Not yet implemented")
            }

            override fun execute(
                identifier: Int?,
                sql: String,
                parameters: Int,
                binders: (SqlPreparedStatement.() -> Unit)?
            ): QueryResult<Long> {
                TODO("Not yet implemented")
            }

            override fun <R> executeQuery(
                identifier: Int?,
                sql: String,
                mapper: (SqlCursor) -> QueryResult<R>,
                parameters: Int,
                binders: (SqlPreparedStatement.() -> Unit)?
            ): QueryResult<R> {
                TODO("Not yet implemented")
            }

            override fun newTransaction(): QueryResult<Transacter.Transaction> {
                TODO("Not yet implemented")
            }

            override fun notifyListeners(vararg queryKeys: String) {
                TODO("Not yet implemented")
            }

            override fun removeListener(vararg queryKeys: String, listener: Query.Listener) {
                TODO("Not yet implemented")
            }
        })
    }
}