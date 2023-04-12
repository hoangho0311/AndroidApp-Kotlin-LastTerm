package com.example.lasttermdemo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lasttermdemo3.Fragment.CurrentOrderFragment
import com.example.lasttermdemo3.Fragment.HistoryOrderFragment
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.btnFavor
import kotlinx.android.synthetic.main.activity_home.btnHome
import kotlinx.android.synthetic.main.activity_home.btnProfile
import kotlinx.android.synthetic.main.activity_home.loadingProgress

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        btnHome.setOnClickListener{
            loadingProgress.visibility = View.GONE
            loadingProgress.animate().setDuration(200).alpha(1f).withEndAction{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

        btnFavor.setOnClickListener{
            loadingProgress.visibility = View.GONE
            loadingProgress.animate().setDuration(200).alpha(1f).withEndAction{
                val intent = Intent(this, FavoriteActivity::class.java)
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

        val sub1 = CurrentOrderFragment()
        val sub2 = HistoryOrderFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, sub1)
            commit()
        }

        currentOrderBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, sub1)
                commit()
            }
        }

        historyOrderBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout, sub2)
                commit()
            }
        }
    }
}