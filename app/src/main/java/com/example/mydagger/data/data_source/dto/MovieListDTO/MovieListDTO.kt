package com.example.mydagger.data.data_source.dto.MovieListDTO

data class MovieListDTO(
    val page : Int,
    val results : ArrayList<MovieItemDTO>,
    val totalResults : Int,
    val total_pages : Int
)
