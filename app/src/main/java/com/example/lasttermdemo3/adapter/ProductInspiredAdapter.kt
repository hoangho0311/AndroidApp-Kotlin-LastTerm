package com.example.lasttermdemo3.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lasttermdemo3.Model.CartItem
import com.example.lasttermdemo3.R
import com.example.lasttermdemo3.databinding.InspiredProductsBinding

class ProductInspiredAdapter(val context: Context, val list: ArrayList<CartItem>) : RecyclerView.Adapter<ProductInspiredAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: InspiredProductsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(InspiredProductsBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView.text = list[position].productName
        Glide.with(context).load(list[position].image).placeholder(R.drawable.man).into(holder.binding.Image)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
