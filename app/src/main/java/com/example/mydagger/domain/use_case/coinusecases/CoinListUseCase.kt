package com.example.mydagger.domain.use_case.coinusecases

import com.example.mydagger.domain.model.Coin
import com.example.mydagger.domain.repository.CoinRepository
import com.example.mydagger.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinListUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    operator fun invoke(): Flow<ResponseState<List<Coin>>> = flow {
        try {
            emit(ResponseState.Loading<List<Coin>>())
            val coins = coinRepository.getAllCoins().map {
                it.toCoin()
            }
            emit(ResponseState.Success<List<Coin>>(coins))
        } catch (e: HttpException) {
            emit(ResponseState.Error<List<Coin>>(e.localizedMessage ?: "Unexpected error"))
        } catch (e: IOException) {
            emit(ResponseState.Error<List<Coin>>("error Occurred"))
        }
    }
}