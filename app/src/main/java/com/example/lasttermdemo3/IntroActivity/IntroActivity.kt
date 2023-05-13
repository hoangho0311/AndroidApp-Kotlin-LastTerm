package com.example.lasttermdemo3.IntroActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.lasttermdemo3.Login.LoginActivity
import com.example.lasttermdemo3.Login.LoginPhoneActivity
import com.example.lasttermdemo3.Login.SignUpActivity
import com.example.lasttermdemo3.MainActivity
import com.example.lasttermdemo3.Model.Product
import com.example.lasttermdemo3.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_intro.*

class introActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference
    private val introSliderAdapter = IntroSliderAdapter(
        listOf(IntroSlide("SiuuuShop","alo", R.drawable.intro),IntroSlide("SiuuuShop","delivery", R.drawable.intro),
            IntroSlide("SiuuuShop","Refund", R.drawable.intro))
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

//        dbRef = FirebaseDatabase.getInstance().getReference("Products")
//
//        val empId = dbRef.push().key!!
//        val employee = Product(empId, "AMIRI SKEL TOP LOW BLACK WHITE", "", "1,650,000đ"
//            ,"https://firebasestorage.googleapis.com/v0/b/lasttermdemo2.appspot.com/o/products%2Famiri-skel-top-low-black-white.jpg?alt=media&token=886fc4ad-273d-4c53-88b1-1d73fd8be930","VANS")
//
//
//        val empId2 = dbRef.push().key!!
//        val employee2 = Product(empId, "AMIRI SKEL TOP LOW ORANGE", "", "1,650,000đ"
//            ,"https://firebasestorage.googleapis.com/v0/b/lasttermdemo2.appspot.com/o/products%2Famiri-skel-top-low-orange.jpg?alt=media&token=d38a94fd-aab2-479e-858d-24d2910ccb5a","VANS")
//        val empId3 = dbRef.push().key!!
//        val employee3 = Product(empId, "VANS FEAR OF GOD NAM, NỮ", "", "500,000đ"
//            ,"https://firebasestorage.googleapis.com/v0/b/lasttermdemo2.appspot.com/o/products%2Fvans-fear-of-god-nam-nu.jpg?alt=media&token=21641399-91d9-49cf-bdaf-22a004558f21","VANS")
//
//        dbRef.child(empId).setValue(employee)
//        dbRef.child(empId3).setValue(employee3)
//        dbRef.child(empId2).setValue(employee2)
//
//        Toast.makeText(this,"Ok", Toast.LENGTH_SHORT).show()

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
                Intent(applicationContext, LoginActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        buttonSkip.setOnClickListener {
            Intent(applicationContext, LoginActivity::class.java).also {
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