package com.example.lasttermdemo3.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lasttermdemo3.R
import com.example.lasttermdemo3.databinding.FragmentSearchBinding
import com.example.lasttermdemo3.databinding.FragmentSearchTabsBinding

class SearchTabsFragment : Fragment() {

    private lateinit var binding: FragmentSearchTabsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearchTabsBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment

        return binding.root
    }

}