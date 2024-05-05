package di

import data.KVStorage
import data.KVStorageImpl
import presentation.home.di.homeModule
import presentation.settings.di.settingsModule
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {
    single<KVStorage> { KVStorageImpl() }
}

val commonModule: List<Module> = listOf(appModule, homeModule, settingsModule)