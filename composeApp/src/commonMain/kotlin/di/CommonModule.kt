package di

import common.data.CategoryDataSource
import common.data.KVStorage
import common.data.KVStorageImpl
import core.Dispatchers
import core.DispatchersImpl
import database.DatabaseFactory
import feature.expense.editor.di.expenseEditorModule
import feature.home.di.homeModule
import feature.settings.di.settingsModule
import org.koin.core.module.Module
import org.koin.dsl.module

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
