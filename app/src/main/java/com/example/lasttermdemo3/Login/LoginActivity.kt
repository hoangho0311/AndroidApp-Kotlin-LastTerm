package com.example.lasttermdemo3.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lasttermdemo3.IntroActivity.LoadingSceneActivity
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
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val pass = binding.passInput.text.toString()

            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if(it.isSuccessful){
                    val intent= Intent(this, LoadingSceneActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        binding.signupBtn.setOnClickListener{
            val SignUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(SignUpIntent)
        }

        binding.forgotPassBtn.setOnClickListener {
            val ForgotIntent = Intent(this, ForgotPassActivity::class.java)
            startActivity(ForgotIntent)
        }
    }
}