package feature.expense.common.di

import feature.expense.common.data.ExpenseDataSource
import feature.expense.expenseEditor.ExpenseEditorViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val expenseModule = module {
    viewModelOf(::ExpenseEditorViewModel)
    factory { ExpenseDataSource(get(), get()) }
}
