package com.example.lasttermdemo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.btnCart
import kotlinx.android.synthetic.main.activity_home.btnHome
import kotlinx.android.synthetic.main.activity_home.btnProfile
import kotlinx.android.synthetic.main.activity_home.loadingProgress

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        btnHome.setOnClickListener{
            loadingProgress.visibility = View.GONE
            loadingProgress.animate().setDuration(200).alpha(1f).withEndAction{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

        btnCart.setOnClickListener{
            loadingProgress.visibility = View.GONE
            loadingProgress.animate().setDuration(200).alpha(1f).withEndAction{
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

        btnProfile.setOnClickListener{
            loadingProgress.visibility = View.GONE
            loadingProgress.animate().setDuration(200).alpha(1f).withEndAction{
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

    }
}