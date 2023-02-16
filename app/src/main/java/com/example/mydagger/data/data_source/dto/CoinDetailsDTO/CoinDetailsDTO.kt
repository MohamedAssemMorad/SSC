package com.example.mydagger.data.data_source.dto.CoinDetailsDTO

data class CoinDetailsDTO(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val country_origin: String,
    val genesis_date: String,
    val market_cap: Long,
    val price: Double,
    val description: String
)
