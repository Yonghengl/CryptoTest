package com.example.cryptotest

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.cryptotest.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

const val TAG = "CroptyTest"

class MainActivity : FragmentActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        setContentView(mBinding.root);
        supportFragmentManager.beginTransaction()
            .add(R.id.main_frame, WalletFragment(), "wallet")
            .commitNow();
        lifecycleScope.launch {
            ExchangeRateManager.instance.loadExchangeRate(this@MainActivity);
            TokenDataManager.instance.loadTokenDetails(this@MainActivity);
        }

    }
}
