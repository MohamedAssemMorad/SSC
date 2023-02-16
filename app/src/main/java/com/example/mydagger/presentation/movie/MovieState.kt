package com.example.mydagger.presentation.movie

import com.example.mydagger.domain.model.Movie

data class MovieState(
    val isLoading: Boolean = false,
    val movies: List<Movie>? = emptyList(),
    val error: String = ""
)