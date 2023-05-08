package com.example.lasttermdemo3.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.lasttermdemo3.Model.UserModel
import com.example.lasttermdemo3.R
import com.example.lasttermdemo3.config
import com.example.lasttermdemo3.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        config.showDialog(requireContext())
        binding = FragmentProfileBinding.inflate(layoutInflater)

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().currentUser!!.uid!!).get().addOnSuccessListener {
            if(it.exists()){
                val data= it.getValue(UserModel::class.java)

                binding.name.setText(data!!.name.toString())
                binding.city.setText(data!!.city.toString())
                binding.email.setText(data!!.email.toString())
                binding.number.setText(data!!.number.toString())

                val image= data.image
                Glide.with(this).load(image).placeholder(R.drawable.man).into(binding.userImage)

                config.hideDialog()
            }
        }
        return binding.root
    }

}