package com.example.digiturktask.presentation.Movies

import com.example.digiturktask.data.data_source.dto.MoviesListDTO.MoviesListDTO
import com.example.digiturktask.data.data_source.dto.MoviesListDTO.Result

data class MoviesState(
    val isLoading: Boolean = false,
    val moviesListDTO: MoviesListDTO? = null,
    val error: String = ""
)