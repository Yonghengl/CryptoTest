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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class WalletFragment : Fragment() {
    private lateinit var mBinding: FragmentWalletBinding
    private val walletViewModel: WalletViewModel = WalletViewModel()

    private var mAdapter: MainAdapter? = null;
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
        val combinedFlow =
            combine(
                walletViewModel.walletItems,
                ExchangeRateManager.instance.exchangeRate,
                TokenDataManager.instance.tokenDetails
            )
            { walletItems, exchangeRates, tokenDetails ->
                Triple(walletItems, exchangeRates, tokenDetails)
            }
        lifecycleScope.launch {
            launch {
                combinedFlow.collect { (walletItems, exchangeRates, tokenDetails) ->
                    Log.e(
                        TAG, " combine back " + walletItems.size + " -- " + exchangeRates.size
                    )
                    if (walletItems.isNotEmpty()) {
                        if (mAdapter == null) {
                            mAdapter = activity?.let {
                                MainAdapter(walletItems, it) { item ->
                                    handleItemClick(item)
                                }
                            }
                            mBinding.recyclerView.adapter = mAdapter
                        } else {
                            mAdapter?.setItems(walletItems)
                            mAdapter?.notifyDataSetChanged()
                        }
                    }
                    walletViewModel.calculateTotal()
                }
            }
            launch {
                walletViewModel.totalBalance.collect { totalBalance ->
                    Log.i(TAG, " total balance change")
                    mBinding.tvMoney.text = CurrencyManager.instance.getCurrentCurrency().symbol()
                    mBinding.tvBalance.text = DataFormat.formatAmount(totalBalance, 0, 2)
                    mBinding.tvCurrency.text = CurrencyManager.instance.getCurrentCurrency().name
                }
            }
            launch {
                CurrencyManager.instance.currentCurrency.collect() {
                    if (mBinding.recyclerView.adapter?.itemCount != 0) {
                        mBinding.recyclerView.adapter?.notifyDataSetChanged()
                        walletViewModel.calculateTotal()
                    }
                }
            }
        }

        mBinding.iconSetting.setOnClickListener {
            if (CurrencyManager.instance.getCurrentCurrency() == CurrencyManager.Currency.USD) {
                CurrencyManager.instance.setCurrentCurrency(CurrencyManager.Currency.AUD)
            } else {
                CurrencyManager.instance.setCurrentCurrency(CurrencyManager.Currency.USD)
            }
        }
    }


    fun handleItemClick(item: Models.WalletBalance) {
        Log.i(TAG, "Clicked: ${item.currency} - ${item.amount}")
    }
}


