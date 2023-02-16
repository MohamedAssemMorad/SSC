package com.example.mydagger.data.data_source.dto.MovieListDTO

import com.example.mydagger.domain.model.Movie

data class MovieItemDTO(
    val title: String,
    val Year: String,
    val imdbID: String,
    val Type: String,
    val poster_path: String
) {
    fun toMovie(): Movie {
        return Movie(title = title, year = Year, id = imdbID, type = Type, image = poster_path)
    }
}
