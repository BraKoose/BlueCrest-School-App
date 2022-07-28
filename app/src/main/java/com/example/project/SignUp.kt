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
import kotlinx.android.synthetic.main.activity_verify_user.*

class SignUp : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.createAccount.setOnClickListener {
            startActivity(Intent(this, DashBoard::class.java))
        }

    }

    fun buSignIn(view: View) {
        mAuth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
            .addOnCompleteListener(this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = mAuth.currentUser
                    if (user != null) {
                        updateUI(user)
                    }
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }

                // ...
            }

    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)

            startActivity(intent)

            finish()
        } else {
//            Toast.makeText(applicationContext, "Authentication failed.",
//                Toast.LENGTH_SHORT).show();
        }

    }


}