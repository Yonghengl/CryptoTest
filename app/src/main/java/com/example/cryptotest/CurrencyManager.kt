package com.example.cryptotest

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CurrencyManager {
    enum class Currency {
        ERD,
        USD,
        AUD,
        CAD,
        GBP;

        fun symbol(): String {
            return when (this) {
                ERD -> "₺"
                USD -> "$"
                AUD -> "AUD$"
                CAD -> "CA$"
                GBP -> "£"
            }
        }
    }

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            CurrencyManager()
        }
    }

    private val _currentCurrency = MutableStateFlow(Currency.USD)
    val currentCurrency: StateFlow<Currency> get() = _currentCurrency;

    // 获取当前货币
    fun getCurrentCurrency(): Currency {
        return _currentCurrency.value
    }

    // 设置当前货币
    fun setCurrentCurrency(currency: Currency) {
        _currentCurrency.value = currency
    }

}