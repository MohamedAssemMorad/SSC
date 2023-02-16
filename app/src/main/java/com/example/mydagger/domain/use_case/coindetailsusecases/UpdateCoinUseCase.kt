package com.example.mydagger.domain.use_case.coindetailsusecases

import com.example.mydagger.domain.model.Coin
import com.example.mydagger.domain.repository.CoinRepository
import com.example.mydagger.utils.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UpdateCoinUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    operator fun invoke(coin: Coin): Flow<ResponseState<Unit>> = flow {
        try {
            emit(ResponseState.Loading<Unit>())
            coinRepository.updateCoin(coin)
            emit(ResponseState.Success<Unit>(Unit))
        } catch (e: HttpException) {
            emit(ResponseState.Error<Unit>(e.localizedMessage ?: "Unexpected error"))
        } catch (e: IOException) {
            emit(ResponseState.Error<Unit>("error Occurred"))
        }
    }
}