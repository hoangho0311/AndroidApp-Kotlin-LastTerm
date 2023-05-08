package com.example.lasttermdemo3.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lasttermdemo3.Model.CartItem
import com.example.lasttermdemo3.adapter.CartAdapter
import com.example.lasttermdemo3.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)

        getData()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getData() {

        // Retrieve cart data from Firebase database
        val user = FirebaseAuth.getInstance().currentUser

        FirebaseDatabase.getInstance().getReference("carts/${user?.uid}/items").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list = arrayListOf<CartItem>()
                    for (data in snapshot.children){
                        val model = data.getValue(CartItem::class.java)
                        list.add(model!!)
                    }
                    list.shuffle()
                    val mAdapter = CartAdapter(requireContext(),list)
                    binding.recyclerView.adapter = mAdapter
                }else
                    Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}