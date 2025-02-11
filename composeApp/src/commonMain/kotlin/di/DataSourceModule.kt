package di

import feature.expense.common.data.ExpenseDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { ExpenseDataSource(get(), get()) }
}
