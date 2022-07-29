package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.project.dashBoards.DashBoard
import com.example.project.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_verify_user.*

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Intialize Firebase Auth
        auth = Firebase.auth



        //Redirects to Login
        val oldUser = binding.newUserLink
        oldUser.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }


        //lets get email and password from the user

        performSignUp()

    }

    private fun performSignUp() {
        val email = binding.editTextTextEmail
    }


}