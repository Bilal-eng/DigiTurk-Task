package com.example.digiturktask.presentation.MovieList

import com.example.digiturktask.data.data_source.dto.GenresListDTO.Genre

data class MovieListState(
    val isLoading: Boolean = false,
    val genres: List<Genre> = emptyList(),
    val error: String = ""
)