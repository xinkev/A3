package di

import core.Dispatchers
import core.DispatchersImpl
import data.CategoryDataSource
import data.KVStorage
import data.KVStorageImpl
import database.DatabaseFactory
import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.feature.home.di.homeModule
import presentation.feature.home.expense.di.expenseEditorModule
import presentation.feature.settings.di.settingsModule

val appModule = module {
    single<KVStorage> { KVStorageImpl() }
    factory<Dispatchers> { DispatchersImpl() }
    single { get<DatabaseFactory>().create() }
    factory { CategoryDataSource(get(), get()) }
}

val commonModule: List<Module> = listOf(
    appModule,
    dataSourceModule,
    homeModule,
    settingsModule,
    expenseEditorModule
)