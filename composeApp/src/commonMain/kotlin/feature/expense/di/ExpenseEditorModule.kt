package feature.expense.di

import feature.expense.editor.presentation.ExpenseEditorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val expenseModule = module {
    viewModelOf(::ExpenseEditorViewModel)
}
