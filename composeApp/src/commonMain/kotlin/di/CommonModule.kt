package di

import feature.expense.editor.di.expenseEditorModule
import feature.home.di.homeModule
import feature.settings.di.settingsModule
import org.koin.core.module.Module

val commonModule: List<Module> = listOf(
    appModule,
    dataSourceModule,
    homeModule,
    settingsModule,
    expenseEditorModule
)
