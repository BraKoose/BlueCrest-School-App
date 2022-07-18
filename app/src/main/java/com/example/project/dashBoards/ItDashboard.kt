package com.example.project.dashBoards

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.project.*
import com.example.project.databinding.ActivityItDashboardBinding
import com.example.project.questions.Cplus
import com.example.project.questions.DiscreteMaths
import com.example.project.questions.Networking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ItDashboard : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityItDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mAuth = FirebaseAuth.getInstance()


        binding.butCplus.setOnClickListener {
            val intent = Intent(this, Cplus::class.java)
            startActivity(intent)
        }

        binding.discreteMathBut.setOnClickListener {
            val intent = Intent(this, DiscreteMaths::class.java)
            startActivity(intent)
        }

        binding.networkingBut.setOnClickListener {
            val intent = Intent(this, Networking::class.java)
            startActivity(intent)
        }

    }


    // SHOW MENU ITEMS
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    // MOVE TO NEW ACTIVITY WHEN MENU ITEM IS SELECTED
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnu_Transcript -> {
                //  setContentView(R.layout.activity_transcript)
                this.startActivity(Intent(this, Transcript::class.java))
                return true
            }


            R.id.mnu_Calculator -> {
                // setContentView(R.layout.activity_gpa__calculator)
                this.startActivity(Intent(this, GpaCalculator::class.java))
                return true
            }
            R.id.mnu_Sign_Out -> {
                signOut()
                // setContentView(R.layout.activity_gpa__calculator)
                this.startActivity(Intent(this, VerifyUser::class.java))
                return true
            }
           R.id.mnu_Timeline ->{
//                this.startActivity(Intent(this,MainActivity::class.java))
                val currentUser = mAuth.currentUser
                updateUI(currentUser )
                return true
            }
            R.id.mnu_pastQuestions -> {
                this.startActivity(Intent(this, DashBoard::class.java))
                return true
            }

            else ->
                return super.onOptionsItemSelected(item)
        }
    }

    fun signOut() {
        mAuth.signOut()
        finish()
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
