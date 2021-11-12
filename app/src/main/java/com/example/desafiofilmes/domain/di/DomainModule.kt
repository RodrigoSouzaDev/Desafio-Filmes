package com.example.desafiofilmes.domain.di

import com.example.desafiofilmes.domain.usecases.GetMovieByIdUseCase
import com.example.desafiofilmes.domain.usecases.GetSimilarMoviesUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load(){
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { GetMovieByIdUseCase(get()) }
            factory { GetSimilarMoviesUseCase(get()) }
        }
    }
}