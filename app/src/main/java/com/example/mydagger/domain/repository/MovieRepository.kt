package com.example.mydagger.domain.repository

import com.example.mydagger.data.data_source.dto.MovieListDTO.MovieListDTO

interface MovieRepository {
    suspend fun getMovieByTitle(apiKey: String): MovieListDTO
}