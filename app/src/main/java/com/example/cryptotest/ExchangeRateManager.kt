package com.example.cryptotest

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptotest.model.Models
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Currency

class ExchangeRateManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ExchangeRateManager()
        }
    }

    private val _exchangeRate = MutableLiveData<List<Models.CurrencyExchange>>(emptyList())
    val exchangeRate: LiveData<List<Models.CurrencyExchange>> get() = _exchangeRate;
    suspend fun loadExchangeRate(context: Context) {
        FetchData(context).fetchExchangeRate().collect { items ->
            Log.i(TAG, "rate down");
            if (items.ok) {
                _exchangeRate.value = items.tiers
            }
        }
    }

    fun getTokenCurrency(symbol: String, amount: Double, format: Boolean = true): String {
        val tokenRate = getTokenRate(
            symbol,
            amount,
            CurrencyManager().getCurrentCurrency().name
        )
        if (!format) {
            return NumberUtils.multiply(amount.toString(), tokenRate)
        }
        return DataFormat.formatAmount(NumberUtils.multiply(amount.toString(), tokenRate));
    }


    fun getTokenRate(symbol: String, amount: Double): String {
        return getTokenRate(
            symbol,
            amount,
            CurrencyManager.instance.getCurrentCurrency().name
        )
    }

    fun getTokenRate(symbol: String, amount: Double, currencyTo: String): String {
        var rate: String = "0";
        if (_exchangeRate.value?.size!! > 0) {
            _exchangeRate.value?.forEach { data ->
                if (data.from_currency.equals(symbol, ignoreCase = true)) {
                    if (data.rates.isNotEmpty()) {
                        rate = data.rates.get(0).rate;
                    }
                }
            }
            if (!currencyTo.equals(CurrencyManager.Currency.USD.name, ignoreCase = true)) {
                _exchangeRate.value?.forEach { data ->
                    if (data.from_currency.equals(currencyTo, ignoreCase = true)) {
                        if (data.rates.isNotEmpty()) {
                            rate = NumberUtils.divide(rate, data.rates.get(0).rate)
                        }
                    }
                }
            }
        }
        return rate;
    }
}

