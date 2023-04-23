package com.example.lasttermdemo3.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.lasttermdemo3.IntroActivity.LoadingSceneActivity
import com.example.lasttermdemo3.databinding.ActivityLoginPhoneBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit

class LoginPhoneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPhoneBinding

    val auth = FirebaseAuth.getInstance()
    private var verificatonId: String? = null

    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = AlertDialog.Builder(this).setView(com.example.lasttermdemo3.R.layout.loading_layout).setCancelable(false).create()

        binding.sendOTP.setOnClickListener {
            if(binding.userphone.text!!.isEmpty()){
                binding.userphone.error = "Please enter your number"
            }else{
                sendOtp(binding.userphone.text.toString())
            }
        }

        binding.verifyOTP.setOnClickListener {
            if(binding.userOTP.text!!.isEmpty()){
                binding.userOTP.error = "Please enter your number"
            }else{
                verifyOtp(binding.userOTP.text.toString())
            }
        }
    }

    private fun verifyOtp(otp: String) {
        dialog.show()
        val credential = PhoneAuthProvider.getCredential(verificatonId!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun sendOtp(number: String) {
        dialog.show()
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                dialog.dismiss()
                signInWithPhoneAuthCredential(credential)
            }
            override fun onVerificationFailed(e: FirebaseException) {
            }
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                this@LoginPhoneActivity.verificatonId = verificationId
                dialog.dismiss()
                binding.numberLayout.visibility= GONE
                binding.otpLayout.visibility=VISIBLE
            }
        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+84$number")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    checkUserExist("+84"+binding.userphone.text.toString())
                } else {
                    dialog.dismiss()
                }
            }
    }

    private fun checkUserExist(number: String) {
        FirebaseDatabase.getInstance().getReference("users").child(number).addValueEventListener(
            object : ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    dialog.dismiss()
                    Toast.makeText(this@LoginPhoneActivity, error.message, Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        dialog.dismiss()
                        startActivity(Intent(this@LoginPhoneActivity, LoadingSceneActivity::class.java))
                        finish()
                    }else{
                        val phone = binding.userphone.text.toString()
                        val intent1 = Intent(this@LoginPhoneActivity, SignUpPhoneActivity::class.java)
                        intent1.putExtra("phone", phone)
                        startActivity(intent1)
                        finish()
                    }
                }
            }
        )
    }
}