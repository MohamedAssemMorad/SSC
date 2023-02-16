package com.example.mydagger.data.repository

import com.example.mydagger.data.data_source.AppApi
import com.example.mydagger.data.data_source.CoinDao
import com.example.mydagger.data.data_source.dto.CoinListDTO.CoinListDTO
import com.example.mydagger.domain.model.Coin
import com.example.mydagger.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImp @Inject constructor(
    private val api: AppApi,
    private val coinDao: CoinDao
) : CoinRepository {

    override suspend fun getAllCoins(): CoinListDTO {
        return api.getCoinList()
    }

    override suspend fun getAllCoinsFromDB(): List<Coin> = coinDao.getAllCoinsFromDB()

    override suspend fun insertAllCoins(coins: List<Coin>) = coinDao.insertAllCoins(coins)

    override suspend fun getLikedCoins(): List<Coin> = coinDao.getLikedCoins()

    override suspend fun updateCoin(coin: Coin) = coinDao.updateCoin(coin)

}