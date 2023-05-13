package com.example.lasttermdemo3.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.lasttermdemo3.Fragment.FragmentProducts.*

class ViewPaperAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
        ): FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return if(position==0)
            AllProductFragment()
        else if(position==1)
            NikeProductFragment()
        else if(position==2)
            AdidasProductFragment()
        else if(position==3)
            PumaProductFragment()
        else
            JordanProductFragment()

    }

}