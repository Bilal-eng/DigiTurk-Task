package com.example.digiturktask.domain.repository

import com.example.digiturktask.data.data_source.dto.GenresListDTO.GenresListDTO
import com.example.digiturktask.data.data_source.dto.MoviesListDTO.MoviesListDTO

interface MovieRepository {

    suspend fun getMovieGenres(): GenresListDTO

    suspend fun getMoviesById(withGenres: Int): MoviesListDTO
}