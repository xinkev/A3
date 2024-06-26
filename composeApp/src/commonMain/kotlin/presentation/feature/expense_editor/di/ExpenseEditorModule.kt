package presentation.feature.expense_editor.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import presentation.feature.expense_editor.ExpenseEditorViewModel

val expenseEditorModule = module {
    viewModelOf(::ExpenseEditorViewModel)
}