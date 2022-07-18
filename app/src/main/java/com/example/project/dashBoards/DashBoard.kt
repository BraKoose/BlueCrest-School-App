package com.example.project.dashBoards

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.project.*
import com.example.project.databinding.ActivityItDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_dash_board.*
import kotlinx.android.synthetic.main.activity_dash_board.view.*


class DashBoard : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityItDashboardBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

//         var itLogo= binding.root.itLogo
//         var massLogo = binding.root.massLogo
//         var fashionLogo = binding.root.fashionLogo
//         var businessLogo = binding.root.businessLogo


        itLogo.setOnClickListener {
            val intent = Intent(this, ItDashboard::class.java)
            startActivity(intent)
        }

        massLogo.setOnClickListener {
            val intent = Intent(this, MassComDashboard::class.java)
            startActivity(intent)
        }

        fashionLogo.setOnClickListener {
            val intent = Intent(this, FashionDashboard::class.java)
            startActivity(intent)
        }
        businessLogo.setOnClickListener {
            val intent = Intent(this, BizDashboard::class.java)
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

            R.id.mnu_pastQuestions -> {
                this.startActivity(Intent(this, DashBoard::class.java))
                return true
            }
            R.id.mnu_Timeline ->{
//                this.startActivity(Intent(this,MainActivity::class.java))
                val currentUser = mAuth.currentUser
                updateUI(currentUser )
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
