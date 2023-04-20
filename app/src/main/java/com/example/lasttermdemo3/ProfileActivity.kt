package com.example.lasttermdemo3

import android.content.Intent
import android.graphics.Bitmap.Config
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lasttermdemo3.Model.UserModel
import com.example.lasttermdemo3.config.hideDialog
import com.example.lasttermdemo3.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_home.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private var imageUri:Uri?=null

    private val selectedImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri = it
        binding.userImage.setImageURI(imageUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userImage.setOnClickListener {
            selectedImage.launch("image/*")
        }

        binding.saveData.setOnClickListener {
            validateData()
        }

        btnHome.setOnClickListener{
            loadingProgress.visibility = View.GONE
            loadingProgress.animate().setDuration(200).alpha(1f).withEndAction{
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

        btnCart.setOnClickListener{
            loadingProgress.visibility = View.GONE
            loadingProgress.animate().setDuration(200).alpha(1f).withEndAction{
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

        btnFavor.setOnClickListener{
            loadingProgress.visibility = View.GONE
            loadingProgress.animate().setDuration(200).alpha(1f).withEndAction{
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    private fun validateData() {
        if(binding.username.text.toString().isEmpty()||binding.useremail.text.toString().isEmpty()||binding.userphonenumber.text.toString().isEmpty()||imageUri==null){
            Toast.makeText(this,"error", Toast.LENGTH_SHORT).show()
        }else {
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
                Toast.makeText(this,"Fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun storeData(it: Uri?) {
        val data= UserModel(name= binding.username.text.toString(),
            email= binding.username.text.toString(),
            image = binding.username.text.toString(),)

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!).setValue(data).addOnCompleteListener {
            hideDialog()
            if(it.isSuccessful){
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
                Toast.makeText(this,"Ok", Toast.LENGTH_SHORT).show()
            }else
            {
                hideDialog()
            }
        }
    }
}