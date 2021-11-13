package com.example.desafiofilmes.data.di

import android.util.Log
import com.example.desafiofilmes.data.repository.MovieRepository
import com.example.desafiofilmes.data.repository.MovieRepositoryImpl
import com.example.desafiofilmes.data.service.TmdbService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {
    private const val OKHTTP = "Okhttp"
    private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"

    fun load(){
        loadKoinModules(networkModules()+ repositoryModules())
    }

    private fun networkModules(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor{
                    Log.e(OKHTTP,it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<TmdbService>(get(),get())
            }
        }
    }

    private fun repositoryModules(): Module{
        return module {
            single<MovieRepository> { MovieRepositoryImpl(get())}
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .client(client)
            .addConverterFactory(factory)
            .build().create(T::class.java)
    }
}