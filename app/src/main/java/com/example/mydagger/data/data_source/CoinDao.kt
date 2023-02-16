package com.example.mydagger.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.mydagger.domain.model.Coin

@Dao
interface CoinDao {
    @Query ("Select * from coin")
    fun getAllCoinsFromDB() : List<Coin>

    @Insert
    fun insertAllCoins(coins : List<Coin>)

    @Query ("Select * from coin where `like` = 1 ")
    fun getLikedCoins() : List<Coin>

    @Update
    fun updateCoin(coin: Coin)
}