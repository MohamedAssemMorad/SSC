package com.example.mydagger.presentation.coinList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydagger.domain.model.Coin
import com.example.mydagger.domain.use_case.coinusecases.CoinListUseCase
import com.example.mydagger.domain.use_case.coinusecases.CoinsFromDBUseCase
import com.example.mydagger.domain.use_case.coinusecases.InsertAllCoinsUseCase
import com.example.mydagger.domain.use_case.coinusecases.LikedCoinsFromDBUseCase
import com.example.mydagger.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val coinListUseCase: CoinListUseCase,
    private val insertAllCoinsUseCase: InsertAllCoinsUseCase,
    private val getCoinsFromDBUseCase: CoinsFromDBUseCase,
    private val likedCoinsFromDBUseCase: LikedCoinsFromDBUseCase
) : ViewModel() {
    private val coinValue = MutableStateFlow(CoinListState())
    var _coinValue: StateFlow<CoinListState> = coinValue

    private val insertCoinValue = MutableStateFlow(InsertCoinListState())
    var _insertCoinValue: StateFlow<InsertCoinListState> = insertCoinValue

    fun getAllCoins() =
        viewModelScope.launch(Dispatchers.IO) {
            coinListUseCase().collect {
                when (it) {
                    is ResponseState.Success -> {
                        coinValue.value = CoinListState(coins = it.data)
                    }
                    is ResponseState.Loading -> {
                        coinValue.value = CoinListState(isLoading = true)
                    }
                    is ResponseState.Error -> {
                        coinValue.value =
                            CoinListState(error = it.message ?: "unExpected Error Occurred")
                    }
                }
            }
        }

    fun insertAllCoins(coins: List<Coin>) = viewModelScope.launch {
        insertAllCoinsUseCase(coins).collect {
            when (it) {
                is ResponseState.Success -> {
                    insertCoinValue.value = InsertCoinListState(success = true)
                }
                is ResponseState.Loading -> {
                    insertCoinValue.value = InsertCoinListState(isLoading = true)
                }
                is ResponseState.Error -> {
                    insertCoinValue.value =
                        InsertCoinListState(error = it.message ?: "unExpected Error Occurred")
                }
            }

        }
    }

    fun getAllCoinsFromDB() = viewModelScope.launch {
        getCoinsFromDBUseCase().collect {
            when (it) {
                is ResponseState.Success -> {
                    coinValue.value = CoinListState(coins = it.data)
                }
                is ResponseState.Loading -> {
                    coinValue.value = CoinListState(isLoading = true)
                }
                is ResponseState.Error -> {
                    coinValue.value =
                        CoinListState(error = it.message ?: "unExpected Error Occurred")
                }
            }

        }
    }

    fun getLikedCoins() = viewModelScope.launch {
        likedCoinsFromDBUseCase().collect {
            when (it) {
                is ResponseState.Success -> {
                    coinValue.value = CoinListState(coins = it.data)
                }
                is ResponseState.Loading -> {
                    coinValue.value = CoinListState(isLoading = true)
                }
                is ResponseState.Error -> {
                    coinValue.value =
                        CoinListState(error = it.message ?: "unExpected Error Occurred")
                }
            }

        }
    }
}