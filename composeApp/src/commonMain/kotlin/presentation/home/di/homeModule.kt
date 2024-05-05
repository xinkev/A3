package presentation.home.di

import org.koin.dsl.module
import presentation.home.HomeViewModel

val homeModule = module {
    factory { HomeViewModel(get()) }
}