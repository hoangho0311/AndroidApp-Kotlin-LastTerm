package com.example.lasttermdemo3.Fragment

import android.icu.text.NumberFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.lasttermdemo3.Model.CartItem
import com.example.lasttermdemo3.adapter.CartAdapter
import com.example.lasttermdemo3.adapter.ProductInspiredAdapter
import com.example.lasttermdemo3.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.math.BigDecimal

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    var databaseReference: DatabaseReference? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)

        databaseReference = FirebaseDatabase.getInstance().getReference("carts/${FirebaseAuth.getInstance().currentUser!!.uid!!}/items")
        databaseReference!!.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded && snapshot.exists()) {
                    val list = arrayListOf<CartItem>()
                    var totalPrice = BigDecimal.ZERO // Initialize totalPrice as BigDecimal
                    for (data in snapshot.children) {
                        val model = data.getValue(CartItem::class.java)
                        list.add(model!!)

                        // Remove commas from price and convert to BigDecimal
                        val priceWithoutCommas = model?.price?.replace(".", "") ?: "0"
                        val itemPrice = BigDecimal(priceWithoutCommas)

                        // Multiply quantity with price
                        val quantity = model?.quantity ?: 0
                        val itemTotalPrice = itemPrice.multiply(BigDecimal(quantity.toString()))

                        totalPrice = totalPrice.add(itemTotalPrice)
                    }
                    list.shuffle()
                    val mAdapter = CartAdapter(requireContext(), list)
                    val iAdapter = ProductInspiredAdapter(requireContext(), list)
                    binding.recyclerView.adapter = mAdapter
                    binding.recyclerViewInspired.adapter = iAdapter

                    // Format and display total price
                    val numberFormat = NumberFormat.getCurrencyInstance()
                    numberFormat.minimumFractionDigits = 0
                    val formattedPrice = numberFormat.format(totalPrice)
                    binding.txtPrice.text = "Total Price: $formattedPrice đ"
                    binding.ToltalPrice.text = "Total Price: $formattedPrice đ"
                } else {
                    Log.d("CartFragment", "Failed to load data")
                }
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle database error if needed
            }
        })


        // Inflate the layout for this fragment
        return binding.root
    }
}