package com.example.desafiofilmes.presentation.di

import com.example.desafiofilmes.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load(){
        loadKoinModules(viewmodelModule())
    }

    private fun viewmodelModule(): Module {
        return module {
            viewModel { MainActivityViewModel(get(),get()) }
        }
    }
}