package com.example.lasttermdemo3.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import com.example.lastterm.adapter.ProductAdapter
import com.example.lasttermdemo3.Model.Product
import com.example.lasttermdemo3.ProductDetails.ProductDetailActivity
import com.example.lasttermdemo3.R
import com.example.lasttermdemo3.adapter.SearchAdapter
import com.example.lasttermdemo3.databinding.FragmentSearchBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_cart.*

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var mAdapter: SearchAdapter
    private var originalList = arrayListOf<Product>()
    private var filteredList = arrayListOf<Product>()
    private var databaseReference: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        databaseReference = FirebaseDatabase.getInstance().getReference("Products")

        mAdapter = SearchAdapter(requireContext(), filteredList)
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.overScrollMode = View.OVER_SCROLL_NEVER

        mAdapter.setOnItemClickListener(object :
            SearchAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("productName", filteredList[position].prdName)
                intent.putExtra("productPrice", filteredList[position].prdPrice)
                intent.putExtra("productDescription", filteredList[position].prdDescrip)
                intent.putExtra("productBrand", filteredList[position].brand)
                intent.putExtra("productID", filteredList[position].prdID)
                intent.putExtra("productImage", filteredList[position].prdImage)

                getActivity()?.startActivity(intent)
            }
        })

        binding.searchViewCurrentLocation.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterList(newText)
                return true
            }
        })

        fetchDataFromDatabase()

        return binding.root
    }

    private fun fetchDataFromDatabase() {
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) {
                    originalList.clear()
                    filteredList.clear()

                    if (snapshot.exists()) {
                        for (data in snapshot.children) {
                            val model = data.getValue(Product::class.java)
                            model?.let { originalList.add(it) }
                        }
                        originalList.shuffle()
                        filteredList.addAll(originalList)
                        mAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "Fail", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error here
            }
        })
    }

    private fun filterList(query: String) {
        filteredList.clear()

        if (query.isEmpty()) {
            filteredList.addAll(originalList)
        } else {
            val searchQuery = query.toLowerCase().trim()

            for (item in originalList) {
                if (item.prdName?.toLowerCase()?.contains(searchQuery) == true) {
                    filteredList.add(item)
                }
            }
        }

        mAdapter.notifyDataSetChanged()
    }
}
