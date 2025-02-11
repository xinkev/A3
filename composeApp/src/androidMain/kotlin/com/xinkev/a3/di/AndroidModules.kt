package com.xinkev.a3.di

import database.AndroidDriverFactory
import core.database.DatabaseFactory
import core.database.DatabaseFactoryImpl
import di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val androidDbModule = module {
    single<DatabaseFactory> {
        DatabaseFactoryImpl(AndroidDriverFactory(androidContext()))
    }
}
val androidModules = androidDbModule + commonModule
