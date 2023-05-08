package com.example.lasttermdemo3.ProductDetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import com.example.lasttermdemo3.Model.CartItem
import com.example.lasttermdemo3.R
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_product_detail2.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail2)
        settingButton()

        val i = intent
        val productNamei = i.getStringExtra("productName")
        val productPricei = i.getStringExtra("productPrice")
        val productDescriptioni = i.getStringExtra("productDescription")
        val productReviewi = i.getStringExtra("productReview")
        val productIDi = i.getStringExtra("productID")
        val cartRef = FirebaseDatabase.getInstance().getReference("carts/${FirebaseAuth.getInstance().currentUser?.uid}")


        textView19.text = productNamei.toString()
        textView18.text = productPricei.toString()
        description_show.text = productDescriptioni.toString()



        button4.setOnClickListener {
            try {
                // Check if the product information is valid
                val productId = productIDi.toString()
                val productName = productNamei.toString()
                val productPrice = productPricei.toString()
                if (productId.isEmpty() || productName.isEmpty() || productPrice.isEmpty()) {
                    Toast.makeText(this, "Please enter valid product information", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Add the product to the cart
                val cartItem = CartItem(productId, productName, productPrice, "1")
                cartRef.child("items").push().setValue(cartItem).addOnSuccessListener {
                    // Show a message to the user
                    Toast.makeText(this, "Product added to cart", Toast.LENGTH_SHORT).show()
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to add product to cart", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Error) {
                // Handle the error if the user is not logged in
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun settingButton() {
        description_btn.setOnClickListener {
            if (description_show.visibility == View.GONE) {
                // Show the text
                description_show.visibility = View.VISIBLE

                // Animate the text
                val anim = AlphaAnimation(0.0f, 1.0f)
                anim.duration = 100
                description_show.startAnimation(anim)
            } else {
                // Hide the text
                description_show.visibility = View.GONE

                // Animate the text
                val anim = AlphaAnimation(1.0f, 0.0f)
                anim.duration = 100
                description_show.startAnimation(anim)
            }
        }

        delivery_btn.setOnClickListener {
            if (delivery_show.visibility == View.GONE) {
                // Show the text
                delivery_show.visibility = View.VISIBLE

                // Animate the text
                val anim = AlphaAnimation(0.0f, 1.0f)
                anim.duration = 100
                delivery_show.startAnimation(anim)
            } else {
                // Hide the text
                delivery_show.visibility = View.GONE

                // Animate the text
                val anim = AlphaAnimation(1.0f, 0.0f)
                anim.duration = 100
                delivery_show.startAnimation(anim)
            }
        }

        review_btn.setOnClickListener {
            if (review_show.visibility == View.GONE) {
                // Show the text
                review_show.visibility = View.VISIBLE

                // Animate the text
                val anim = AlphaAnimation(0.0f, 1.0f)
                anim.duration = 100
                review_show.startAnimation(anim)
            } else {
                // Hide the text
                review_show.visibility = View.GONE

                // Animate the text
                val anim = AlphaAnimation(1.0f, 0.0f)
                anim.duration = 100
                review_show.startAnimation(anim)
            }
        }

        back_btn.setOnClickListener{
            finish()
        }
    }
}