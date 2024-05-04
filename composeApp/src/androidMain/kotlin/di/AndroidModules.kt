package di

import database.AndroidDriverFactory
import database.Database
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val androidDbModule = module {
    single { Database(AndroidDriverFactory(androidContext())) }
}
val androidModules = androidDbModule + commonModule