package com.example.mydagger.di

import android.content.Context
import androidx.room.Dao
import androidx.room.Room
import com.example.mydagger.data.data_source.AppApi
import com.example.mydagger.data.data_source.CoinDao
import com.example.mydagger.data.data_source.room.AppDatabase
import com.example.mydagger.data.repository.CoinRepositoryImp
import com.example.mydagger.data.repository.MovieRepositoryImp
import com.example.mydagger.domain.repository.CoinRepository
import com.example.mydagger.domain.repository.MovieRepository
import com.example.mydagger.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppApi(): AppApi {
        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(AppApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app, AppDatabase::class.java, "coins.db"
    ).allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideCoinDao(db: AppDatabase) = db.coinDao()

    @Provides
    @Singleton
    fun provideMovieRepository(appApi: AppApi): MovieRepository {
        return MovieRepositoryImp(appApi)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(appApi: AppApi, dao: CoinDao): CoinRepository {
        return CoinRepositoryImp(appApi, dao)
    }
}