package common.di

import common.data.KVStorage
import common.data.KVStorageImpl
import core.Dispatchers
import core.DispatchersImpl
import core.database.DatabaseFactory
import core.event.EventBus
import core.file.FileManager
import feature.category.common.data.CategoryDataSource
import org.koin.dsl.module

val appModule = module {
    single<KVStorage> { KVStorageImpl() }
    factory<Dispatchers> { DispatchersImpl() }
    single { get<DatabaseFactory>().create() }
    factory { CategoryDataSource(get(), get()) }
    single { EventBus() }
    factory { FileManager }
}
