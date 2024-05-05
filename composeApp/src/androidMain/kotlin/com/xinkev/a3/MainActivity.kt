package com.xinkev.a3

import core.App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.xinkev.a3.di.androidModules
import di.startKoin
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            startKoin(
                appDeclaration = {
                    androidContext(this@MainActivity)
                },
                modules = androidModules
            ) {
                App()
            }
        }
    }
}