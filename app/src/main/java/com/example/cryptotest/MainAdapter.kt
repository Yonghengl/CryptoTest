package com.example.cryptotest

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptotest.model.Models

class MainAdapter(
    private val items: List<Models.WalletBalance>,
    private val context: Context,
    private val onItemClick: (Models.WalletBalance) -> Unit
) :
    RecyclerView.Adapter<MainAdapter.WalletViewHolder>() {

    inner class WalletViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvToken: TextView = view.findViewById(R.id.token)
        private val tvAmount: TextView = view.findViewById(R.id.amount)
        private val tvCurrency: TextView = view.findViewById(R.id.currency)
        private val icon: AppCompatImageView = view.findViewById(R.id.icon)

        @SuppressLint("SetTextI18n")
        fun bind(item: Models.WalletBalance) {


            val token: Models.Token? = TokenDataManager.instance.getToken(item.currency);
            if (null != token) {
                Glide.with(context).load(token.colorful_image_url)
                    .placeholder(R.drawable.icon_wallet)
                    .into(icon)
                tvToken.text = token.name
                tvAmount.text = StringBuffer().append(item.amount.toString())
                    .append(" ").append(item.currency)

            } else {
                tvToken.text = item.currency
                tvAmount.text = item.amount.toString()
            }

            tvCurrency.text = StringBuffer().append(
                CurrencyManager.instance.getCurrentCurrency()
                    .symbol()
            ).append(" ").append(
                ExchangeRateManager.instance.getTokenCurrency(
                    item.currency,
                    item.amount
                )
            ).toString();
            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_item, parent, false)
        return WalletViewHolder(view)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}