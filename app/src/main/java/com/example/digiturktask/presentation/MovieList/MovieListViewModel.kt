package com.example.digiturktask.presentation.MovieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digiturktask.domain.use_cases.GenresListUseCase
import com.example.digiturktask.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListUseCase: GenresListUseCase
) : ViewModel() {

    private val movieListValue = MutableStateFlow(MovieListState())
    var _movieListValue: StateFlow<MovieListState> = movieListValue

    fun getMovieGenres() = viewModelScope.launch(Dispatchers.IO) {
        movieListUseCase().collect {
            when (it) {
                is ResponseState.Success -> {
                    movieListValue.value = MovieListState(genres = it.data ?: emptyList())
                }

                is ResponseState.Loading -> {
                    movieListValue.value = MovieListState(isLoading = true)
                }

                is ResponseState.Error -> {
                    movieListValue.value =
                        MovieListState(error = it.message ?: "An Unexpected Error")
                }
            }
        }
    }
}