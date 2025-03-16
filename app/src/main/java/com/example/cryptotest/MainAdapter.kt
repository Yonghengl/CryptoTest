package com.example.cryptotest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptotest.model.Models

class MainAdapter(
    private val items: List<Models.WalletBalance>,
    private val onItemClick: (Models.WalletBalance) -> Unit
) :
    RecyclerView.Adapter<MainAdapter.WalletViewHolder>() {

    inner class WalletViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val currencyTextView: TextView = view.findViewById(R.id.token)
        private val amountTextView: TextView = view.findViewById(R.id.amount)

        fun bind(item: Models.WalletBalance) {
            currencyTextView.text = item.currency
            amountTextView.text = item.amount.toString()
            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item, parent, false)
        return WalletViewHolder(view)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}