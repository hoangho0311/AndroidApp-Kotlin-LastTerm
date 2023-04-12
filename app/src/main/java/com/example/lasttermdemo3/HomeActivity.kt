package com.example.lasttermdemo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat.animate
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lastterm.adapter.ProductAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var dbRef : DatabaseReference
    private lateinit var ds:ArrayList<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        btnFavor.setOnClickListener{
            loadingProgress.visibility = View.GONE
            loadingProgress.animate().setDuration(200).alpha(1f).withEndAction{
                val intent = Intent(this, FavoriteActivity::class.java)
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
//
//        dbRef = FirebaseDatabase.getInstance().getReference("Products")
//        val prdId= dbRef.push().key!!
//        val pr = Product(prdId,"Sport Leather","Lorem ipsum is a placeholder text commonly used","$33.09","4")
//
//        dbRef.child(prdId).setValue(pr).addOnCompleteListener {
//            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener{
//            Toast.makeText(this, "false", Toast.LENGTH_SHORT).show()
//        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)

        ds = arrayListOf<Product>()
        GetProduct()
    }

    private fun GetProduct() {
        dbRef=FirebaseDatabase.getInstance().getReference("Products")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if(snapshot.exists()){
                    for(proSnap in snapshot.children){
                        val proData = proSnap.getValue(Product::class.java)
                        ds.add(proData!!)
                    }
                    val mAdapter = ProductAdapter(ds)
                    recyclerView.adapter = mAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}