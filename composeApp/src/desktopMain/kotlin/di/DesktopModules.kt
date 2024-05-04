package di

import database.Database
import database.DesktopDriverFactory
import org.koin.dsl.module

val desktopDbModule = module {
    single { Database(DesktopDriverFactory()) }
}

val desktopModules = desktopDbModule + commonModule