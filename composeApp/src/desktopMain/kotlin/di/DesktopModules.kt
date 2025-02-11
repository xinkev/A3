package di

import core.database.DatabaseFactory
import core.database.DatabaseFactoryImpl
import database.DesktopDriverFactory
import org.koin.dsl.module

val desktopDbModule = module {
    single<DatabaseFactory> {
        DatabaseFactoryImpl(DesktopDriverFactory())
    }
}

val desktopModules = desktopDbModule + commonModule
