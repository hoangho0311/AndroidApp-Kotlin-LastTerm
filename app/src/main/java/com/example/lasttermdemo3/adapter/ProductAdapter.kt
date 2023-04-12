package com.example.lastterm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lasttermdemo3.Product
import com.example.lasttermdemo3.R
import kotlinx.android.synthetic.main.product_items.view.*

class ProductAdapter(private val ds:ArrayList<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_items, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            txtNameProduct.text = ds[position].prdName
            txtDecription.text = ds[position].prdDescrip
            txtPrice.text = ds[position].prdPrice
            txtReview.text = ds[position].prdReview
        }
    }

    override fun getItemCount(): Int {
         return ds.size
    }
}