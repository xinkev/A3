package feature.expense.editor.di

import feature.expense.editor.presentation.ExpenseEditorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val expenseEditorModule = module {
    viewModelOf(::ExpenseEditorViewModel)
}
