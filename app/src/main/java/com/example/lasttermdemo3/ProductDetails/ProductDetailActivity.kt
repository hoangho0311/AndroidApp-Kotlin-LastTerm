package com.example.lasttermdemo3.ProductDetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.lasttermdemo3.Model.CartItem
import com.example.lasttermdemo3.R
import com.google.common.primitives.UnsignedBytes.toInt
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_product_detail2.*


class ProductDetailActivity : AppCompatActivity() {
    var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail2)
        settingButton()

        val i = intent
        val productNamei = i.getStringExtra("productName")
        val productPricei = i.getStringExtra("productPrice")
        val productBrandi = i.getStringExtra("productBrand")
        val productIDi = i.getStringExtra("productID")
        val productImagei = i.getStringExtra("productImage")


        textView19.text = productNamei.toString()
        textView17.text= productBrandi.toString()
        txtPrice.text = "Price: "+productPricei.toString()
        Glide.with(this).load(productImagei).placeholder(R.drawable.man).into(imageProduct)

        databaseReference = FirebaseDatabase.getInstance().getReference("carts/${FirebaseAuth.getInstance().currentUser?.uid}")

        button4.setOnClickListener {
            try {
                // Check if the product information is valid
                val productId = productIDi.toString()
                val productName = productNamei.toString()
                val productPrice = productPricei.toString()

                // Get the selected radio button value
                val selectedRadioButtonId = radioGroup.checkedRadioButtonId

                if (selectedRadioButtonId == -1) {
                    // No size selected, show a message to the user
                    Toast.makeText(this@ProductDetailActivity, "Please select a size", Toast.LENGTH_SHORT).show()
                } else {
                    val selectedRadioButton: RadioButton = findViewById(selectedRadioButtonId)
                    val selectedValue = selectedRadioButton.text.toString()

                    // Check if the product already exists in the cart
                    val cartItemRef = databaseReference!!.child("items")
                    val query = cartItemRef.orderByChild("productId").equalTo(productId)
                    val itemId = cartItemRef.push().key
                    query.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Product already exists in the cart, increment the quantity
                                for (itemSnapshot in dataSnapshot.children) {
                                    val existingCartItem = itemSnapshot.getValue(CartItem::class.java)
                                    existingCartItem?.let {
                                        val currentQuantity = it.quantity?.toIntOrNull() ?: 0
                                        val quantity = currentQuantity + 1
                                        itemSnapshot.ref.child("quantity").setValue(quantity.toString())
                                    }
                                }
                            } else {
                                // Product does not exist in the cart, add it as a new item
                                val cartItem = CartItem(itemId ,productId, productName, selectedValue, productPrice, "1", productImagei.toString())
                                cartItemRef.child(itemId!!).setValue(cartItem)
                            }
                            // Show a message to the user
                            Toast.makeText(this@ProductDetailActivity, "Product added to cart", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Handle the database error
                            Toast.makeText(this@ProductDetailActivity, "Failed to add product to cart", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            } catch (e: Exception) {
                // Log the error message
                Log.e("ProductDetailActivity", "Error adding product to cart: ${e.message}")
                // Show a toast with the error message
                Toast.makeText(this, "An error occurred while adding the product to cart", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun settingButton() {

        back_btn.setOnClickListener{
            finish()
        }
    }
}