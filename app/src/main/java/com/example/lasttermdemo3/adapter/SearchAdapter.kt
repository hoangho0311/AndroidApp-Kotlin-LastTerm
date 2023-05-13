package com.example.lasttermdemo3.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lastterm.adapter.ProductAdapter
import com.example.lasttermdemo3.Model.Product
import com.example.lasttermdemo3.R
import com.example.lasttermdemo3.databinding.ProductItemsBinding
import com.example.lasttermdemo3.databinding.SearchItemsBinding
import kotlinx.android.synthetic.main.search_items.view.*

class SearchAdapter(
    private val context: Context,
    private val productList: ArrayList<Product>
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.search_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.itemView.name.text = productList[position].prdName
        // Bind the data to the views in your ViewHolder
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define your ViewHolder views and set listeners if needed
        init {
            itemView.setOnClickListener {
                if (::mListener.isInitialized) {
                    mListener.onItemClick(adapterPosition)
                }
            }
        }
    }
}