package com.example.cryptotest

class CurrencyManager {
    enum class Currency(val description: String) {
        ERD("ERD"),
        USD("USD"),
        AUD("AUD"),
        CAD("CAD"),
        GBP("GBP");

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

    private var currentCurrency: Currency = Currency.USD

    // 获取当前货币
    fun getCurrentCurrency(): Currency {
        return currentCurrency
    }

    // 设置当前货币
    fun setCurrentCurrency(currency: Currency) {
        currentCurrency = currency
    }

}