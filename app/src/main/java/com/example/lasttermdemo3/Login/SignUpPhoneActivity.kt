package com.example.lasttermdemo3.Login

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lasttermdemo3.HomeActivity
import com.example.lasttermdemo3.IntroActivity.LoadingSceneActivity
import com.example.lasttermdemo3.Model.UserModel
import com.example.lasttermdemo3.R
import com.example.lasttermdemo3.config
import com.example.lasttermdemo3.config.hideDialog
import com.example.lasttermdemo3.databinding.ActivityLoginPhoneBinding
import com.example.lasttermdemo3.databinding.ActivitySignUpPhoneBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class SignUpPhoneActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpPhoneBinding

    private var imageUri:Uri? = null

    private val selectIamge = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri = it
        binding.userImage.setImageURI(imageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userImage.setOnClickListener {
            selectIamge.launch("image/*")
        }

        binding.saveData.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        if(binding.userName.text.toString().isEmpty()||binding.userEmail.text.toString().isEmpty()||binding.userCity.text.toString().isEmpty()||imageUri==null){
            Toast.makeText(this,"Please enter all fields", Toast.LENGTH_SHORT).show()
        }else if(!binding.termsCondition.isChecked){
            Toast.makeText(this,"Please accept terms and conditions", Toast.LENGTH_SHORT).show()
        }else
        {
            uploadImage()
        }
    }

    private fun uploadImage() {
        config.showDialog(this)

        val storageRef = FirebaseStorage.getInstance().getReference("profile").child(FirebaseAuth.getInstance().currentUser!!.uid).child("profile.jpg")

        storageRef.putFile(imageUri!!).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                storeData(it)
                Toast.makeText(this,"ok", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                hideDialog()
                Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun storeData(it: Uri?) {
        val data= UserModel(name= binding.userName.text.toString(),
            email= binding.userEmail.text.toString(),
            city = binding.userCity.text.toString(),
            image= imageUri.toString(),)

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!).setValue(data).addOnCompleteListener {
            hideDialog()
            if(it.isSuccessful){
                startActivity(Intent(this, LoadingSceneActivity::class.java))
                finish()
                Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
            }else
            {
                hideDialog()
            }
        }
    }
}