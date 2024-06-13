package presentation.feature.home.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.home.HomeViewModel

val homeModule = module {
    viewModelOf(::HomeViewModel)
}