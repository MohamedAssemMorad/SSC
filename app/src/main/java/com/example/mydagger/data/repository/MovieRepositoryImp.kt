package com.example.mydagger.data.repository

import com.example.mydagger.data.data_source.AppApi
import com.example.mydagger.data.data_source.dto.MovieListDTO.MovieListDTO
import com.example.mydagger.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val api: AppApi
) : MovieRepository {

    override suspend fun getMovieByTitle(
        apiKey: String
    ): MovieListDTO {
        return api.getMovieByTitleAndPage(apiKey)
    }
}