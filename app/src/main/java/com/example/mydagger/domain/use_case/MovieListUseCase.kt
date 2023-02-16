package com.example.mydagger.domain.use_case

import com.example.mydagger.domain.model.Movie
import com.example.mydagger.domain.repository.MovieRepository
import com.example.mydagger.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(
        apiKey: String
    ): Flow<ResponseState<List<Movie>>> = flow {
        try {
            emit(ResponseState.Loading<List<Movie>>())
            val movies = movieRepository.getMovieByTitle(apiKey).results.map {
                it.toMovie()
            }
            emit(ResponseState.Success<List<Movie>>(movies))
        } catch (e: HttpException) {
            emit(ResponseState.Error<List<Movie>>(e.localizedMessage ?: "Unexpected error"))
        } catch (e: IOException) {
            emit(ResponseState.Error<List<Movie>>("error Occurred"))
        }
    }
}