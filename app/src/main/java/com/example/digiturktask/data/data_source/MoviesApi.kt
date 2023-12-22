package com.example.digiturktask.data.data_source

import com.example.digiturktask.data.data_source.dto.GenresListDTO.GenresListDTO
import com.example.digiturktask.data.data_source.dto.MoviesListDTO.MoviesListDTO
import com.example.digiturktask.util.Constants.API_KEY
import com.example.digiturktask.util.Constants.LANGUAGE
import com.example.digiturktask.util.Constants.SORT_BY
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApi {
    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = LANGUAGE
    ): GenresListDTO

    @GET("discover/movie")
    suspend fun getMoviesById(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("sort_by") sortBy: String = SORT_BY,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("page") page: Int = 1,
        @Query("with_genres") withGenres: Int
    ): MoviesListDTO

}