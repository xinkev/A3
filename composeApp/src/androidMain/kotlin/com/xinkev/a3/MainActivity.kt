package com.xinkev.a3

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import database.AndroidDriverFactory
import database.Database
import di.startKoin
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    private val db by lazy {
        Database(AndroidDriverFactory(this@MainActivity))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            startKoin(appDeclaration = {
                androidContext(this@MainActivity)
            }) {
                App(db)
            }
        }
    }
}