package di

import database.Database
import database.IOSDriverFactory
import org.koin.dsl.module

val iosDbModule = module {
    single { Database(IOSDriverFactory()) }
}

val iosModules = iosDbModule + commonModule