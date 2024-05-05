package database

import com.xinkev.a3.sqldelight.A3Database

interface DatabaseFactory {
    fun create(): A3Database
}

class DatabaseFactoryImpl(driverFactory: DriverFactory) : DatabaseFactory {
    private val db by lazy { A3Database(driverFactory.create()) }

    override fun create() = db
}