package com.example.digiturktask.presentation.Movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digiturktask.domain.use_cases.MoviesUseCase
import com.example.digiturktask.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private val moviesValue = MutableStateFlow(MoviesState())
    var _moviesValue: StateFlow<MoviesState> = moviesValue

    fun getMoviesById(withGenres: Int) = viewModelScope.launch(Dispatchers.IO) {
        moviesUseCase(withGenres = withGenres).collect {
            when (it) {
                is ResponseState.Success -> {
                    moviesValue.value = MoviesState(moviesListDTO = it.data)
                }

                is ResponseState.Loading -> {
                    moviesValue.value = MoviesState(isLoading = true)
                }

                is ResponseState.Error -> {
                    moviesValue.value = MoviesState(error = it.message ?: "An Unexpected Error")
                }
            }
        }
    }
}