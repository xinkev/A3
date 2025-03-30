package common.di

import feature.category.common.di.categoriesModule
import feature.expense.di.expenseModule
import feature.home.di.homeModule
import feature.settings.common.di.settingsModule
import org.koin.core.module.Module

val commonModule: List<Module> = listOf(
    appModule,
    dataSourceModule,
    homeModule,
    expenseModule,
    categoriesModule,
) + settingsModule
