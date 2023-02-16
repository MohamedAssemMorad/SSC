package com.example.mydagger.presentation.coinList

data class InsertCoinListState(
    val isLoading: Boolean = false,
    val success : Boolean = false,
    val error: String = ""
)