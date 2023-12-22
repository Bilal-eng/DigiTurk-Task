package com.example.digiturktask.domain.use_cases

import com.example.digiturktask.data.data_source.dto.MoviesListDTO.MoviesListDTO
import com.example.digiturktask.domain.repository.MovieRepository
import com.example.digiturktask.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class MoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(withGenres: Int): Flow<ResponseState<MoviesListDTO>> = flow {

        try {

            emit(ResponseState.Loading())
            val movies = repository.getMoviesById(withGenres)

            emit(ResponseState.Success(movies))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An Unexpected Error"))
        } catch (e: IOException) {
            emit(ResponseState.Error("Internet Error"))
        }
    }
}