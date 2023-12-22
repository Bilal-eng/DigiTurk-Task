package com.example.digiturktask.domain.use_cases

import com.example.digiturktask.data.data_source.dto.GenresListDTO.Genre
import com.example.digiturktask.domain.repository.MovieRepository
import com.example.digiturktask.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GenresListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<ResponseState<List<Genre>>> = flow {
        try {
            emit(ResponseState.Loading())
            val genresList = repository.getMovieGenres()
            emit(ResponseState.Success(genresList.genres))
        } catch (e: HttpException) {
            emit(ResponseState.Error(e.localizedMessage ?: "An Unexpected Error"))
        } catch (e: IOException) {
            emit(ResponseState.Error("Internet Error"))
        }
    }
}