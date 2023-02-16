package com.example.mydagger.presentation.coinList

import com.example.mydagger.domain.model.Coin

data class CoinListState(
    val isLoading: Boolean = false,
    val coins : List<Coin>? = emptyList(),
    val error: String = ""
)