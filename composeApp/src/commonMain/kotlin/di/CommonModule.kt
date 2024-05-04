package di

import data.KVStorage
import data.KVStorageImpl
import home.di.homeModule
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {
    single <KVStorage> { KVStorageImpl() }
}

val commonModule: List<Module> = listOf(appModule, homeModule)