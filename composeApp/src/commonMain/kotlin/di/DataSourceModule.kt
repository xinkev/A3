package di

import feature.expense.data.ExpenseDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { ExpenseDataSource(get(), get()) }
}
