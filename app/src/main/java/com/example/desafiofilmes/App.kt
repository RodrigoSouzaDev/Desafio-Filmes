package com.example.desafiofilmes

import android.app.Application
import com.example.desafiofilmes.data.di.DataModule
import com.example.desafiofilmes.domain.di.DomainModule
import com.example.desafiofilmes.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            DataModule.load()
            PresentationModule.load()
            DomainModule.load()
        }
    }
}