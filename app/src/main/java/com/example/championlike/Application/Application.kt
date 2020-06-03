package com.example.championlike.Application

import android.app.Activity
import android.app.Application
import com.example.championlike.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CustomApplication: Application(){


    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@CustomApplication)
            modules(listOf(networkModule, viewModelModule, useCaseModule, repositoryModule, appModule))
        }
    }
}