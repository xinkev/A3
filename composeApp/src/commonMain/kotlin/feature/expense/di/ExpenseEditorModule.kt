package feature.expense.di

import feature.expense.expense_editor.presentation.ExpenseEditorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val expenseModule = module {
    viewModelOf(::ExpenseEditorViewModel)
}
