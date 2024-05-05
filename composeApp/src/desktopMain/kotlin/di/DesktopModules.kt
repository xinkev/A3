package di

import database.DatabaseFactory
import database.DatabaseFactoryImpl
import database.DesktopDriverFactory
import org.koin.dsl.module

val desktopDbModule = module {
    single<DatabaseFactory> {
        DatabaseFactoryImpl(DesktopDriverFactory())
    }
}

val desktopModules = desktopDbModule + commonModule