package com.example.digiturktask.data.data_source.dto.MoviesListDTO

data class MoviesListDTO(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)