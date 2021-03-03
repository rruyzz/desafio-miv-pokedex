package com.example.pokedex.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin(){
        val appModule = mutableListOf(module)
        startKoin{
            androidLogger(Level.ERROR)
            androidContext(this@CustomApplication)
            modules(appModule)
        }
    }
}