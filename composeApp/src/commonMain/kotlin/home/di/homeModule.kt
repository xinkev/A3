package home.di

import home.HomeViewModel
import org.koin.dsl.module

val homeModule = module {
    factory { HomeViewModel(get(), get()) }
}