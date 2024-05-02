package com.xinkev.a3

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.xinkev.a3.data.AndroidDriverFactory
import data.AppDatabase

class MainActivity : ComponentActivity() {
    private val database by lazy {
        AppDatabase(AndroidDriverFactory(context = applicationContext))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(database)
        }
    }
}