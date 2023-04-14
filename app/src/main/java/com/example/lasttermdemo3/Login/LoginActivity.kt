package com.example.lasttermdemo3.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lasttermdemo3.IntroActivity.introActivity
import com.example.lasttermdemo3.R
import com.example.lasttermdemo3.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {
            val email = binding.textView24.text.toString()
            val pass = binding.textView25.text.toString()

            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if(it.isSuccessful){
                    val intent= Intent(this, introActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}