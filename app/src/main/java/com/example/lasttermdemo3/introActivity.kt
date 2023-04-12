package com.example.lasttermdemo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro.*

class introActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        imageButton2.setOnClickListener{
            val i = Intent(this, LoadingSceneActivity::class.java)
            startActivity(i)
        }
    }
}