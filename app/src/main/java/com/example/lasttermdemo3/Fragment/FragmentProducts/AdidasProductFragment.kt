package com.example.lasttermdemo3.Fragment.FragmentProducts

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lastterm.adapter.ProductAdapter
import com.example.lasttermdemo3.Model.Product
import com.example.lasttermdemo3.ProductDetails.ProductDetailActivity
import com.example.lasttermdemo3.R
import com.example.lasttermdemo3.databinding.FragmentAdidasProductBinding
import com.example.lasttermdemo3.databinding.FragmentNikeProductBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdidasProductFragment : Fragment() {
    private lateinit var binding: FragmentAdidasProductBinding
    val cartRef = FirebaseDatabase.getInstance().getReference("Products")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdidasProductBinding.inflate(layoutInflater)

        cartRef.orderByChild("brand").equalTo("ADIDAS").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (isAdded) { // Check if the fragment is added to the activity
                        val context = requireContext()
                        if (snapshot.exists()) {
                            val list = arrayListOf<Product>()
                            for (data in snapshot.children) {
                                val model = data.getValue(Product::class.java)
                                list.add(model!!)
                            }
                            list.shuffle()
                            val mAdapter = ProductAdapter(requireContext(), list)
                            binding.recyclerview.adapter = mAdapter
                            binding.recyclerview.overScrollMode = View.OVER_SCROLL_NEVER

                            mAdapter.setOnItemClickListener(object :
                                ProductAdapter.onItemClickListener {
                                override fun onItemClick(position: Int) {
                                    val intent = Intent(context, ProductDetailActivity::class.java)
                                    intent.putExtra("productName", list[position].prdName)
                                    intent.putExtra("productPrice", list[position].prdPrice)
                                    intent.putExtra("productDescription", list[position].prdDescrip)
                                    intent.putExtra("productBrand", list[position].brand)
                                    intent.putExtra("productID", list[position].prdID)
                                    intent.putExtra("productImage", list[position].prdImage)

                                    getActivity()?.startActivity(intent)
                                }
                            })
                        } else
                            Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
        )

        return binding.root
    }
}