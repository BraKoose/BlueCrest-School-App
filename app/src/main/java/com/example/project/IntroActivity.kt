package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.Login.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
        binding.signUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }
}