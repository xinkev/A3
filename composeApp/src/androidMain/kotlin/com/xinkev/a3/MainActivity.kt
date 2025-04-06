package com.xinkev.a3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.App
import common.di.appModule
import core.file.FileManager
import io.github.vinceglb.filekit.core.FileKit
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FileKit.init(this)
        FileManager.init(this)
        enableEdgeToEdge()
        setContent {
            KoinApplication(
                application = {
                    modules(appModule)
                    androidContext(applicationContext)
                }
            ) {
                App()
            }
        }
    }
}
