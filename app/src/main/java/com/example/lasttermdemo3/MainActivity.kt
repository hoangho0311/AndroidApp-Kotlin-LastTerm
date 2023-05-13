package com.example.lasttermdemo3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.lasttermdemo3.Fragment.CartFragment
import com.example.lasttermdemo3.Fragment.FavouriteFragment
import com.example.lasttermdemo3.Fragment.HomeFragment
import com.example.lasttermdemo3.Fragment.ProfileFragment
import com.example.lasttermdemo3.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var databaseRef: DatabaseReference? = null
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseRef = FirebaseDatabase.getInstance().getReference("carts/${FirebaseAuth.getInstance().currentUser!!.uid!!}/items")
        databaseRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemCount = snapshot.childrenCount
                chipNavigationBar.showBadge(R.id.cartFragment, itemCount.toInt())
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error if needed
            }
        })

        val chipNavigationBar: ChipNavigationBar = findViewById(R.id.chipNavigationBar)
        chipNavigationBar.setOnItemSelectedListener { itemId ->
            when (itemId) {
                R.id.homeFragment -> {
                    val fragment = HomeFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit()
                }
                R.id.favouriteFragment -> {
                    val fragment = FavouriteFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit()
                }
                R.id.cartFragment -> {
                    val fragment = CartFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit()
                }
                R.id.profileFragment -> {
                    val fragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit()
                }
            }
        }

        chipNavigationBar.setItemSelected(R.id.homeFragment)
    }
}