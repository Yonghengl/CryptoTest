package com.example.cryptotest

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptotest.model.Models
import com.example.cryptotest.model.reponse.BaseResponse
import com.example.cryptotest.model.reponse.WalletBalanceResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WalletViewModel() : ViewModel() {

    private val _walletItems = MutableStateFlow<List<Models.WalletBalance>>(emptyList())
    val walletItems: StateFlow<List<Models.WalletBalance>> get() = _walletItems
    private val _balance = MutableLiveData<String>("0")

    val totalBalance: LiveData<String> = Transformations.map(_balance) {
        _balance.value?.let { DataFormat.formatAmount(it, 0, 2) }
    }

    fun loadWalletData(context: Context) {
        viewModelScope.launch {
            FetchData(context).fetchWalletData().collect { items ->
                Log.i(TAG, "balance down");
                if (items.ok) {
                    _walletItems.value = items.wallet;
                }
            }
        }
    }

    fun calculateTotal() {
        var total = "0";
        _walletItems.value.forEach { data ->
            val currency = ExchangeRateManager.instance.getTokenCurrency(
                data.currency,
                data.amount,
                false
            )
            total = NumberUtils.add(total, currency);
        }
        _balance.value = total;
    }

}