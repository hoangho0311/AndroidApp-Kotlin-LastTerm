package com.example.lasttermdemo3.IntroActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lasttermdemo3.R
import org.w3c.dom.Text

class IntroSliderAdapter(private val introSlide: List<IntroSlide>): RecyclerView.Adapter<IntroSliderAdapter.IntroSlideViewHolder>(){

    inner class IntroSlideViewHolder(view:View): RecyclerView.ViewHolder(view){
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDecription = view.findViewById<TextView>(R.id.textDescription)
        private val imageIcon = view.findViewById<ImageView>(R.id.imageSlideIcon)

        fun bind(introSlide: IntroSlide){
            textTitle.text = introSlide.title
            textDecription.text = introSlide.description
            imageIcon.setImageResource(introSlide.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_cointainer,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IntroSliderAdapter.IntroSlideViewHolder, position: Int) {
        holder.bind(introSlide[position])
    }

    override fun getItemCount(): Int {
        return introSlide.size
    }
}