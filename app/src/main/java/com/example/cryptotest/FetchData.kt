package com.example.cryptotest

import android.content.Context
import com.example.cryptotest.model.Models
import com.example.cryptotest.model.reponse.WalletBalanceResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class FetchData {
    fun fetchWalletData(context: Context): Flow<WalletBalanceResponse> = flow {
        val jsonString = withContext(Dispatchers.IO) {
            context.assets.open("wallet_balance.json").bufferedReader().use { it.readText() }
        }
        val walletResponse: WalletBalanceResponse =
            Gson().fromJson(jsonString, WalletBalanceResponse::class.java)
        emit(walletResponse)
    }
}