package com.example.cryptotest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptotest.databinding.FragmentWalletBinding
import com.example.cryptotest.model.Models
import kotlinx.coroutines.launch

class WalletFrragment : Fragment() {
    private lateinit var mBinding: FragmentWalletBinding
    private val walletViewModel: WalletViewModel = WalletViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false)
        setData()
        return mBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData() {
        activity?.let { walletViewModel.loadWalletData(it) }
        mBinding.recyclerView.layoutManager = LinearLayoutManager(activity);
        lifecycleScope.launch {
            walletViewModel.walletItems.collect { walletItems ->
                Log.e(TAG, "setadapter   size = " + walletItems.size)
                val adapter = activity?.let {
                    MainAdapter(walletItems, it) { item ->
                        handleItemClick(item)
                    }
                }
                mBinding.recyclerView.adapter = adapter
            }
        }
        walletViewModel.totalBalance.observeForever() {
            Log.i(TAG, " total balance change")
            mBinding.tvMoney.text = CurrencyManager.instance.getCurrentCurrency().symbol()
            mBinding.tvBalance.text = walletViewModel.totalBalance.value
            mBinding.tvCurrency.text = CurrencyManager.instance.getCurrentCurrency().name
        }
        ExchangeRateManager.instance.exchangeRate.observeForever() {
            if (mBinding.recyclerView.adapter?.itemCount != 0) {
                mBinding.recyclerView.adapter?.notifyDataSetChanged()
                walletViewModel.calculateTotal()
            }
        }
        TokenDataManager.instance.tokenDetails.observeForever() {
            if (mBinding.recyclerView.adapter?.itemCount != 0) {
                mBinding.recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    fun handleItemClick(item: Models.WalletBalance) {
        Log.i(TAG, "Clicked: ${item.currency} - ${item.amount}")
    }
}


