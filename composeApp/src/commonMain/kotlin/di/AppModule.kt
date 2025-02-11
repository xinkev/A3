package di

import common.data.CategoryDataSource
import common.data.KVStorage
import common.data.KVStorageImpl
import core.Dispatchers
import core.DispatchersImpl
import core.event.EventBus
import database.DatabaseFactory
import org.koin.dsl.module

val appModule = module {
    single<KVStorage> { KVStorageImpl() }
    factory<Dispatchers> { DispatchersImpl() }
    single { get<DatabaseFactory>().create() }
    factory { CategoryDataSource(get(), get()) }
    single { EventBus() }
}
