package com.example.mydagger.presentation.coindetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydagger.domain.model.Coin
import com.example.mydagger.domain.use_case.coindetailsusecases.UpdateCoinUseCase
import com.example.mydagger.presentation.coinList.InsertCoinListState
import com.example.mydagger.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val updateCoinUseCase: UpdateCoinUseCase
) : ViewModel() {

    private val updateCoinValue = MutableStateFlow(InsertCoinListState())
    var _updateCoinValue: StateFlow<InsertCoinListState> = updateCoinValue

    fun updateCoin(coin: Coin) = viewModelScope.launch {
        updateCoinUseCase(coin).collect {
            when (it) {
                is ResponseState.Success -> {
                    updateCoinValue.value = InsertCoinListState(success = true)
                }
                is ResponseState.Loading -> {
                    updateCoinValue.value = InsertCoinListState(isLoading = true)
                }
                is ResponseState.Error -> {
                    updateCoinValue.value =
                        InsertCoinListState(error = it.message ?: "unExpected Error Occurred")
                }
            }

        }
    }
}