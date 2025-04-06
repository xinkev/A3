package common.di

import common.data.KVStorage
import common.data.KVStorageImpl
import core.Dispatchers
import core.DispatchersImpl
import core.database.DatabaseFactory
import core.event.EventBus
import core.file.FileManager
import feature.category.common.di.categoriesModule
import feature.expense.common.di.expenseModule
import feature.settings.common.di.settingsModule
import org.koin.dsl.module

val appModule = module {
    single<KVStorage> { KVStorageImpl() }
    factory<Dispatchers> { DispatchersImpl() }
    single { DatabaseFactory() }
    single { EventBus() }
    factory { FileManager }
} + listOf(
    categoriesModule,
    expenseModule,
) + settingsModule
