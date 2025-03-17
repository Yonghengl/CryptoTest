package com.example.cryptotest

import android.content.Context
import com.example.cryptotest.model.Models
import com.example.cryptotest.model.reponse.LiveRatesResponse
import com.example.cryptotest.model.reponse.WalletBalanceResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class FetchData(private val context: Context) {
    fun fetchWalletData(): Flow<WalletBalanceResponse> = flow {
        val jsonString = withContext(Dispatchers.IO) {
            Thread.sleep(2000);
            loadJSONFromAsset("wallet_balance.json")
        }
        val walletResponse: WalletBalanceResponse =
            Gson().fromJson(jsonString, WalletBalanceResponse::class.java)
        emit(walletResponse)
    }

    fun fetchExchangeRate(): Flow<LiveRatesResponse> = flow {
        val jsonString = withContext(Dispatchers.IO) {
            Thread.sleep(4000);
            loadJSONFromAsset("live_rates.json")
        }
        val liveRatesResponse: LiveRatesResponse =
            Gson().fromJson(jsonString, LiveRatesResponse::class.java)
        emit(liveRatesResponse)
    }

    private fun loadJSONFromAsset(fileName: String): String {
        val inputStream = context.assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        return reader.use { it.readText() }
    }
}