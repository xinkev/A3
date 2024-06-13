package presentation.feature.expenseEditor.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.expenseEditor.ExpenseEditorViewModel

val expenseEditorModule = module {
    viewModelOf(::ExpenseEditorViewModel)
}