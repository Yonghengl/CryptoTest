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

class TokenDataManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            TokenDataManager()
        }
    }

    private val _tokenDetails = MutableStateFlow<List<Models.Token>>(emptyList())
    val tokenDetails: StateFlow<List<Models.Token>> get() = _tokenDetails;
    suspend fun loadTokenDetails(context: Context) {
        FetchData(context).fetchTokenDetails().collect { items ->
            Log.i(TAG, "token data down");
            if (items.ok) {
                _tokenDetails.value = items.currencies
            } else {
                Log.e(TAG, "loadTokenDetails error , " + items.warning)
            }
        }
    }

    fun getToken(symbol: String): Models.Token? {
        if (_tokenDetails.value?.size!! > 0) {
            _tokenDetails.value?.forEach { data ->
                if (data.symbol.equals(symbol, ignoreCase = true)) {
                    return data
                }
            }
        }
        return null;
    }
}

