package com.example.digiturktask.data.repository

import com.example.digiturktask.data.data_source.MoviesApi
import com.example.digiturktask.data.data_source.dto.GenresListDTO.GenresListDTO
import com.example.digiturktask.data.data_source.dto.MoviesListDTO.MoviesListDTO
import com.example.digiturktask.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi
) : MovieRepository {
    override suspend fun getMovieGenres(): GenresListDTO {
        return moviesApi.getMovieGenres()
    }

    override suspend fun getMoviesById(withGenres: Int): MoviesListDTO {
        return moviesApi.getMoviesById(withGenres = withGenres)
    }
}