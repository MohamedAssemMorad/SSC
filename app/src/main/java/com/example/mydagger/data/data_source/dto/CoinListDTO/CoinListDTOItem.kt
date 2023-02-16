package com.example.mydagger.data.data_source.dto.CoinListDTO


import com.example.mydagger.domain.model.Coin


data class CoinListDTOItem(
    val id: String,
    val symbol: String,
    val name: String
) {
    fun toCoin(): Coin {
        return Coin(dbId = 0, id = id, name = name, symbol = symbol, like = false)
    }
}
