package com.example.lastterm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lasttermdemo3.Model.Product
import com.example.lasttermdemo3.databinding.ProductItemsBinding

class ProductAdapter(val context: Context, val list: ArrayList<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    inner class ViewHolder(val binding: ProductItemsBinding, clickListener: onItemClickListener): RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProductItemsBinding.inflate(LayoutInflater.from(context), parent, false), mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtNameProduct.text = list[position].prdName
        holder.binding.txtPrice.text = list[position].prdPrice
        holder.binding.txtDecription.text = list[position].prdDescrip
    }

    override fun getItemCount(): Int {
        return list.size
    }

}