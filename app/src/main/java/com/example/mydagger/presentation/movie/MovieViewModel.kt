package com.example.mydagger.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydagger.domain.use_case.MovieListUseCase
import com.example.mydagger.utils.Constant
import com.example.mydagger.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase
) : ViewModel() {
    private val movieValue = MutableStateFlow(MovieState())
    var _movieValue: StateFlow<MovieState> = movieValue

    fun getMovieByTitle(apiKey: String = Constant.API_KEY) =
        viewModelScope.launch(Dispatchers.IO) {
            movieListUseCase(apiKey).collect {
                when (it) {
                    is ResponseState.Success -> {
                        movieValue.value = MovieState(movies = it.data)
                    }
                    is ResponseState.Loading -> {
                        movieValue.value = MovieState(isLoading = true)
                    }
                    is ResponseState.Error -> {
                        movieValue.value =
                            MovieState(error = it.message ?: "unExpected Error Occurred")
                    }
                }
            }
        }
}