package com.example.mydagger.domain.repository

import com.example.mydagger.data.data_source.dto.CoinListDTO.CoinListDTO
import com.example.mydagger.domain.model.Coin

interface CoinRepository {
    suspend fun getAllCoins(): CoinListDTO

    suspend fun getAllCoinsFromDB() : List<Coin>

    suspend fun insertAllCoins(coins: List<Coin>)

    suspend fun getLikedCoins() : List<Coin>

    suspend fun updateCoin(coin: Coin)

}