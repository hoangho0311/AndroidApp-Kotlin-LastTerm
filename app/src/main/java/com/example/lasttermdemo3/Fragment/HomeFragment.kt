package com.example.lasttermdemo3.Fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.lasttermdemo3.Model.Product
import com.example.lasttermdemo3.adapter.ViewPaperAdapter
import com.example.lasttermdemo3.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter:ViewPaperAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)



        // Inflate the layout for this fragment
        tabLayout = binding.TabLayout
        viewPager2 = binding.Viewpaper

        adapter = ViewPaperAdapter(childFragmentManager, lifecycle)

        tabLayout.addTab(tabLayout.newTab().setText("ALL"))
        tabLayout.addTab(tabLayout.newTab().setText("Nike"))
        tabLayout.addTab(tabLayout.newTab().setText("ADIDAS"))
        tabLayout.addTab(tabLayout.newTab().setText("VANS"))
        tabLayout.addTab(tabLayout.newTab().setText("JORDAN"))

        viewPager2.adapter=adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if(tab!=null){
                    viewPager2.currentItem=tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt((position)))
            }
        })

        return binding.root
    }


}