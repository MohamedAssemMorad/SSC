package com.example.mydagger.data.data_source

import com.example.mydagger.data.data_source.dto.CoinDetailsDTO.CoinDetailsDTO
import com.example.mydagger.data.data_source.dto.CoinListDTO.CoinListDTO
import com.example.mydagger.data.data_source.dto.MovieListDTO.MovieListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {

    @GET("coins/list")
    suspend fun getCoinList(): CoinListDTO

    @GET("movie/top_rated")
    suspend fun getMovieByTitleAndPage(
        @Query("apikey") apiKey: String
    ): MovieListDTO
}