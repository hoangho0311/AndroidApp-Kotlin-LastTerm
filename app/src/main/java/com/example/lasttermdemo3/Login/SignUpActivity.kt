package com.example.lasttermdemo3.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lasttermdemo3.R
import com.example.lasttermdemo3.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signUpButton.setOnClickListener {
            val email = binding.textView26.text.toString()
            val password = binding.textView27.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}