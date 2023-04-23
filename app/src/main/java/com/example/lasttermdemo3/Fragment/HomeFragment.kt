package com.example.lasttermdemo3.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lastterm.adapter.ProductAdapter
import com.example.lasttermdemo3.Model.Product
import com.example.lasttermdemo3.ProductDetails.ProductDetailActivity
import com.example.lasttermdemo3.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers.Main

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        getData()

        return binding.root
    }

    private fun getData() {
        FirebaseDatabase.getInstance().getReference("Products").addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("ok","${snapshot.toString()}")

                    if(snapshot.exists()){
                        val list = arrayListOf<Product>()
                        for (data in snapshot.children){
                            val model = data.getValue(Product::class.java)
                            list.add(model!!)
                        }
                        list.shuffle()
                        val mAdapter = ProductAdapter(requireContext(),list)
                        binding.recyclerView.adapter = mAdapter
                        binding.recyclerView2.adapter = mAdapter

                        mAdapter.setOnItemClickListener(object :ProductAdapter.onItemClickListener{
                            override fun onItemClick(position: Int) {
                                val intent = Intent (getActivity(), ProductDetailActivity::class.java)
                                getActivity()?.startActivity(intent)
                            }
                        })
                    }else
                        Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
        )
    }

}