package di

import feature.category.di.categoriesModule
import feature.expense.di.expenseModule
import feature.home.di.homeModule
import feature.settings.di.settingsModule
import org.koin.core.module.Module

val commonModule: List<Module> = listOf(
    appModule,
    dataSourceModule,
    homeModule,
    settingsModule,
    expenseModule,
    categoriesModule,
)
