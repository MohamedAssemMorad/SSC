package com.example.mydagger.presentation.coindetails

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mydagger.R
import com.example.mydagger.domain.model.Coin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinDetailsActivity : AppCompatActivity() {


    private var coin: Coin? = null
    private lateinit var id: TextView
    private lateinit var name: TextView
    private lateinit var symbol: TextView

    private lateinit var like: Button
    private lateinit var disLike: Button

    private val coinDetailsViewModel : CoinDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_details)

        id = findViewById(R.id.tvID)
        name = findViewById(R.id.tvName)
        symbol = findViewById(R.id.tvSymbol)
        like = findViewById(R.id.likeBtn)
        disLike = findViewById(R.id.disLikeBtn)

        like.setOnClickListener{
            onLikeClicked(coin!!)
        }

        disLike.setOnClickListener{
            onDisLikeClicked(coin!!)
        }
        getDataFromIntent()
    }

    private fun onDisLikeClicked(coin: Coin) {
        coin.like = false
        coinDetailsViewModel.updateCoin(coin).invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                isDisLiked()
            }
        }
    }

    private fun onLikeClicked(coin: Coin) {
        coin.like = true
        coinDetailsViewModel.updateCoin(coin).invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                isLiked()
            }
        }
    }

    private fun getDataFromIntent() {
        val extras = intent.extras
        if (extras != null) {
            coin = extras.getParcelable("coin")
        }
        setData()
    }

    private fun setData() {
        id.text = coin?.id
        name.text = coin?.name
        symbol.text = coin?.symbol
        if (coin?.like!!)
            isLiked()
        else
            isDisLiked()
    }

    private fun isLiked(){
        like.setBackgroundColor(Color.BLUE)
        disLike.setBackgroundColor(Color.GRAY)
    }

    private fun isDisLiked() {
        like.setBackgroundColor(Color.GRAY)
        disLike.setBackgroundColor(Color.BLUE)
    }
}