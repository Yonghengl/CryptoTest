package com.example.cryptotest.model.reponse

import com.example.cryptotest.model.Models

open class BaseResponse {
    val ok = false
    val warning: String? = null
}

data class WalletBalanceResponse(var wallet: List<Models.WalletBalance>) : BaseResponse();
data class LiveRatesResponse(var tiers: List<Models.CurrencyExchange>) : BaseResponse();
data class CurrenciesResponse(var currencies: List<Models.Token>) : BaseResponse();