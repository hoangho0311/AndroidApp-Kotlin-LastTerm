package com.example.lasttermdemo3.ProductDetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import com.example.lasttermdemo3.R
import kotlinx.android.synthetic.main.activity_product_detail2.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail2)

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
//            val i = Intent(this, HomeActivity::class.java)
//            startActivity(i)
        }
    }
}