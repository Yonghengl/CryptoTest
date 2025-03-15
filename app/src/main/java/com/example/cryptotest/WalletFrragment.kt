package com.example.cryptotest

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false)
        return mBinding.root
    }
}