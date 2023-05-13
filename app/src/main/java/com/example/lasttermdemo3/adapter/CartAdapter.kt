package com.example.lasttermdemo3.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lasttermdemo3.Model.CartItem
import com.example.lasttermdemo3.R
import com.example.lasttermdemo3.databinding.CartItemsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore


class CartAdapter(val context: Context, val list: ArrayList<CartItem>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CartItemsBinding): RecyclerView.ViewHolder(binding.root){
        fun deleteProduct(position: Int) {
            val cartId = list[position].cartId
            val cartItemsRef = FirebaseDatabase.getInstance()
                .getReference("carts/${FirebaseAuth.getInstance().currentUser?.uid}/items")

            cartItemsRef.child(cartId!!).removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Remove the item from the list and notify the adapter
                    list.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }

        fun increaseQuantity(position: Int) {
            val cartId = list[position].cartId
            val currentQuantity = list[position].quantity?.toIntOrNull() ?: 0
            val quantity = currentQuantity + 1
            val cartItemsRef = FirebaseDatabase.getInstance().getReference("carts/${FirebaseAuth.getInstance().currentUser?.uid}/items")

            cartItemsRef.orderByChild("cartId").equalTo(cartId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val itemId = data.key
                        cartItemsRef.child(itemId!!).child("quantity").setValue(quantity.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error if needed
                }
            })
        }

        fun decreaseQuantity(position: Int) {
            val cartId = list[position].cartId
            val currentQuantity = list[position].quantity?.toIntOrNull() ?: 0
            val quantity = currentQuantity - 1
            val cartItemsRef = FirebaseDatabase.getInstance().getReference("carts/${FirebaseAuth.getInstance().currentUser?.uid}/items")

            if (quantity > 0) {
                cartItemsRef.orderByChild("cartId").equalTo(cartId).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (data in snapshot.children) {
                            val itemId = data.key
                            cartItemsRef.child(itemId!!).child("quantity").setValue(quantity.toString())
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle database error if needed
                    }
                })
            } else {
                cartItemsRef.orderByChild("cartId").equalTo(cartId).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (data in snapshot.children) {
                            val itemId = data.key
                            cartItemsRef.child(itemId!!).removeValue().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Remove the item from the list and notify the adapter
                                    list.removeAt(position)
                                    notifyItemRemoved(position)
                                    Toast.makeText(context, "Product removed from cart", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle database error if needed
                    }
                })
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CartItemsBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.productNameTextView.text = list[position].productName
        holder.binding.priceTextView.text = list[position].price
        holder.binding.txtQuantity.text = list[position].quantity
        Glide.with(context).load(list[position].image).placeholder(R.drawable.man).into(holder.binding.Image)

        holder.binding.btnDelete.setOnClickListener {
            holder.deleteProduct(position)
        }

        holder.binding.increaseQuantity.setOnClickListener {
            holder.increaseQuantity(position)
        }

        holder.binding.decreaseQuantity.setOnClickListener {
            holder.decreaseQuantity(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
