package presentation.feature.home.expense.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.home.expense.ExpenseEditorViewModel

val expenseEditorModule = module {
    viewModelOf(::ExpenseEditorViewModel)
}