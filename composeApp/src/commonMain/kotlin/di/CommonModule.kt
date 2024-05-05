package di

import core.Dispatchers
import core.DispatchersImpl
import data.KVStorage
import data.KVStorageImpl
import presentation.home.di.homeModule
import presentation.settings.di.settingsModule
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {
    single<KVStorage> { KVStorageImpl() }
    factory<Dispatchers> { DispatchersImpl() }
}

val commonModule: List<Module> = listOf(appModule, homeModule, settingsModule)