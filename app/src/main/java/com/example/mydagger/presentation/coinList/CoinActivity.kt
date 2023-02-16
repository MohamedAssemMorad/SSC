package com.example.mydagger.presentation.coinList

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mydagger.R
import com.example.mydagger.domain.model.Coin
import com.example.mydagger.presentation.adapter.CoinDelegate
import com.example.mydagger.presentation.adapter.RecyclerViewAdapter
import com.example.mydagger.presentation.coindetails.CoinDetailsActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CoinActivity : AppCompatActivity(), CoinDelegate {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var likedBtn: Button
    private lateinit var likedTxt: TextView
    private lateinit var constraintLayout: ConstraintLayout
    var recyclerViewAdapter: RecyclerViewAdapter? = null
    var coinsList: ArrayList<Coin> = ArrayList()
    private var isLoading = false

    private val coinViewModel: CoinViewModel by viewModels()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        constraintLayout = findViewById(R.id.constraintLayout)
        likedBtn = findViewById(R.id.btnLikedCoins)
        likedTxt = findViewById(R.id.tvLikedCoins)

        likedBtn.setOnClickListener {
            getLikedCoins()
        }
        getCoinsFromDB()
    }

    private fun getLikedCoins() {
        coinViewModel.getLikedCoins().invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                if (!coinViewModel._coinValue.value.coins.isNullOrEmpty())
                    likedTxt.text = coinViewModel._coinValue.value.coins!!.size.toString()
                else
                    likedTxt.text = "0"
            }
        }
    }

    private fun getCoinsFromDB() {
        coinViewModel.getAllCoinsFromDB().invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                if (coinViewModel._coinValue.value.coins.isNullOrEmpty())
                    getCoins()
                else {
                    coinViewModel._coinValue.value.coins?.let { coinsList.addAll(it) }
                    initAdapter()
                    initScrollListener()
                }

            }
        }
    }

    private fun getCoins() {
        progressBar.visibility = View.VISIBLE
        coinViewModel.getAllCoins().invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                progressBar.visibility = View.GONE
                if (coinViewModel._coinValue.value.coins.isNullOrEmpty()) {
                    Snackbar.make(
                        constraintLayout,
                        coinViewModel._coinValue.value.error,
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    coinViewModel._coinValue.value.coins?.let { coinsList.addAll(it) }
                    initAdapter()
                    initScrollListener()
                    insertAllCoins(coinsList)
                }
            }
        }
    }

    private fun insertAllCoins(coinsList: ArrayList<Coin>) {
        progressBar.visibility = View.VISIBLE
        coinViewModel.insertAllCoins(coinsList).invokeOnCompletion {
            progressBar.visibility = View.GONE
            CoroutineScope(Dispatchers.Main).launch {
                if (coinViewModel._insertCoinValue.value.success)
                    Snackbar.make(constraintLayout, "Coins added to db", Snackbar.LENGTH_LONG)
                        .show()
            }
        }
    }

    private fun initScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == coinsList.size - 1) {
                        //bottom of list!
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun initAdapter() {
        recyclerViewAdapter = RecyclerViewAdapter(coinsList)
        recyclerViewAdapter?.callbacks = this
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun loadMore() {
        coinsList.add(Coin(0, "", "", "", false))
        recyclerViewAdapter!!.notifyItemInserted(coinsList.size - 1)
        val handler = Handler()
        handler.postDelayed(Runnable {
            coinsList.removeAt(coinsList.size - 1)
            val scrollPosition: Int = coinsList.size
            recyclerViewAdapter!!.notifyItemRemoved(scrollPosition)
            var currentSize = scrollPosition
            val nextLimit = currentSize + 10
            while (currentSize - 1 < nextLimit) {
                coinsList.add(Coin(0, "", "", "", false))
                currentSize++
            }
            recyclerViewAdapter!!.notifyDataSetChanged()
            isLoading = false
        }, 20000)
    }

    override fun onItemClicked(coin: Coin) {
        val i = Intent(this@CoinActivity, CoinDetailsActivity::class.java)
        i.putExtra("coin", coin)
        startActivity(i)
    }
}