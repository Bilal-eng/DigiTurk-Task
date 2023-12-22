package com.example.digiturktask.di

import com.example.digiturktask.data.data_source.MoviesApi
import com.example.digiturktask.data.repository.MovieRepositoryImpl
import com.example.digiturktask.domain.repository.MovieRepository
import com.example.digiturktask.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MoviesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: MoviesApi): MovieRepository {
        return MovieRepositoryImpl(moviesApi = api)
    }
}