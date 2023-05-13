package com.example.lasttermdemo3.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lasttermdemo3.R
import com.example.lasttermdemo3.databinding.FragmentFavouriteBinding

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(layoutInflater)

        val sub1 = SearchFragment()
        val sub2 = SearchTabsFragment()

        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, sub1)
            commit()
        }

        return binding.root
    }
}

