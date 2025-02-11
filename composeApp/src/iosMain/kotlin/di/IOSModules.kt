package di

import core.database.DatabaseFactory
import core.database.DatabaseFactoryImpl
import database.IOSDriverFactory
import org.koin.dsl.module

val iosDbModule = module {
    single<DatabaseFactory> { DatabaseFactoryImpl(IOSDriverFactory()) }
}

val iosModules = iosDbModule + commonModule
