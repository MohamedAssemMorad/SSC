package com.example.mydagger.data.data_source.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mydagger.data.data_source.CoinDao
import com.example.mydagger.domain.model.Coin
import com.example.mydagger.utils.Converters

@Database(entities = [Coin::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao
}