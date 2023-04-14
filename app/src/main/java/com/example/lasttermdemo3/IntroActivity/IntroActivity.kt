package com.example.lasttermdemo3.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.lasttermdemo3.Login.SignUpActivity
import com.example.lasttermdemo3.R
import kotlinx.android.synthetic.main.activity_intro.*

class introActivity : AppCompatActivity() {
    private val introSliderAdapter = IntroSliderAdapter(
        listOf(IntroSlide("SiuuuShop","alo", R.drawable.intro),IntroSlide("SiuuuShop","delivery", R.drawable.intro),
            IntroSlide("SiuuuShop","Refund", R.drawable.intro))
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        introSliderViewPaper.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPaper.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        imageButton2.setOnClickListener{
            if(introSliderViewPaper.currentItem+1<introSliderAdapter.itemCount){
                introSliderViewPaper.currentItem+=1
            }else{
                Intent(applicationContext, SignUpActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        buttonSkip.setOnClickListener {
            Intent(applicationContext, SignUpActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun setupIndicators(){
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams:LinearLayout.LayoutParams= LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(0,0,0,0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams= layoutParams
            }
            indicatorCointainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index:Int){
        val childCount = indicatorCointainer.childCount
        for(i in 0 until childCount){
            val imageView = indicatorCointainer[i] as ImageView
            if(i == index){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_active)
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive)
                )
            }
        }
    }
}