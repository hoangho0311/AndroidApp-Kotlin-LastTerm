package com.example.lasttermdemo3.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lastterm.adapter.ProductAdapter
import com.example.lasttermdemo3.Model.CartItem
import com.example.lasttermdemo3.Model.Product
import com.example.lasttermdemo3.databinding.CartItemsBinding
import com.example.lasttermdemo3.databinding.ProductItemsBinding


class CartAdapter(val context: Context, val list: ArrayList<CartItem>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CartItemsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CartItemsBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.productNameTextView.text = list[position].productName
        holder.binding.priceTextView.text = list[position].price
        holder.binding.txtQuantity.text = list[position].quantity
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
