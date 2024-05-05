package di

import data.ExpenseDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    factory { ExpenseDataSource(get(), get()) }
}