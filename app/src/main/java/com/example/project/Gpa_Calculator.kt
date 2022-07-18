package com.example.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project.dashBoards.DashBoard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private var mAuth: FirebaseAuth? = null

class Gpa_Calculator : AppCompatActivity() {


    lateinit var e1: EditText
    lateinit var e2: EditText
    lateinit var e3: EditText
    lateinit var e4: EditText
    lateinit var e5: EditText
    lateinit var c1: EditText
    lateinit var c2: EditText
    lateinit var c3: EditText
    lateinit var c4: EditText
    lateinit var c5: EditText
    lateinit var b1: Button
    lateinit var re: Button
    lateinit var aa: TextView
    lateinit var bb: TextView
    lateinit var cc: TextView
    lateinit var dd: TextView
    lateinit var ee: TextView
    lateinit var ff: TextView
    lateinit var gg: TextView
    internal var grade = 0.0


    internal var dsGpa = 0.0
    internal var osGpa = 0.0
    internal var DbmsGpa = 0.0
    internal var swGpa = 0.0
    internal var cpGpa = 0.0
    var d = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpa__calculator)

        mAuth = FirebaseAuth.getInstance()

        //courses
        e1 = findViewById(R.id.ds)
        e2 = findViewById(R.id.db)
        e3 = findViewById(R.id.os)
        e4 = findViewById(R.id.sw)
        e5 = findViewById(R.id.cp)

        //credit hours
        c1 = findViewById(R.id.ch1)
        c2 = findViewById(R.id.ch2)
        c3 = findViewById(R.id.ch3)
        c4 = findViewById(R.id.ch4)
        c5 = findViewById(R.id.ch5)

        //buttons
        b1 = findViewById(R.id.but)
        re = findViewById(R.id.reset)

        //textViews

        aa = findViewById(R.id.ds1)
        bb = findViewById(R.id.ds2)
        cc = findViewById(R.id.ds3)
        dd = findViewById(R.id.ds4)
        ee = findViewById(R.id.ds5)
        gg = findViewById(R.id.fail)
        ff = findViewById(R.id.result)


        //reset button
        re.setOnClickListener {
            gg.text = ""
            ff.text = ""
            ee.text = ""
            dd.text = ""
            cc.text = ""
            bb.text = ""
            aa.text = ""


            e1.setText("")
            e2.setText("")
            e3.setText("")
            e4.setText("")
            e5.setText("")

            c1.setText("")
            c2.setText("")
            c3.setText("")
            c4.setText("")
            c5.setText("")
        }

        //Gpa button
        b1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {

                val a = Integer.parseInt(e1.text.toString())

                val b = Integer.parseInt(e2.text.toString())
                val c = Integer.parseInt(e3.text.toString())
                val d = Integer.parseInt(e4.text.toString())
                val e = Integer.parseInt(e5.text.toString())


                val h1 = Integer.parseInt(c1.text.toString())

                val h2 = Integer.parseInt(c2.text.toString())
                val h3 = Integer.parseInt(c3.text.toString())
                val h4 = Integer.parseInt(c4.text.toString())
                val h5 = Integer.parseInt(c5.text.toString())


                //getting the Grades for each subject.
                val aaa = check(a)
                val bbb = check(b)
                val ccc = check(c)
                val ddd = check(d)
                val eee = check(e)


                val totalHours = h1 + h2 + h3 + h4 + h5


                if (aaa == "E" || bbb == "E" || ccc == "E" || ddd == "E" || eee == "E") {

                    gg.text = "You have to Rewrite some paper(s) "

                } else {
                    gg.text = "Congratulation!! You Passed All Papers"


                }
                if (aaa == "Invalid Mark" || bbb == "Invalid Mark" || ccc == "Invalid Mark" || ddd == "Invalid Mark" || eee == "Invalid Mark") {
                    ff.text = "Invalid Mark(s) Could Not Calculate "
                }

                //setting grades into the textView
                aa.text = aaa
                bb.text = bbb
                cc.text = ccc
                dd.text = ddd
                ee.text = eee


                //getting values

                dsFun(aaa)
                dmbsFun(bbb)
                osFun(ccc)
                swFun(ddd)
                cpFun(eee)


                val mark =
                    ((dsGpa * h1) + (DbmsGpa * h2) + (osGpa * h3) + (swGpa * h4) + (cpGpa * h5)) / totalHours.toFloat()

                Toast.makeText(applicationContext, "You GPA is :$mark", Toast.LENGTH_LONG).show()
                // grade = 0.0


            }

            fun check(n: Int): String {
                if (n in 80..100) {
                    return "A"
                }
                if (n in 75..79) {
                    return "B+"
                }
                if (n in 70..74) {
                    return "B"
                }
                if (n in 65..69) {
                    return "C+"
                }
                if (n in 60..64) {
                    return "C"
                }
                if (n in 55..59) {
                    return "D+"
                }
                if (n in 50..54) {
                    return "D"
                }
                return if (n <= 49) {
                    "E"
                } else
                    "Invalid Mark"
            }

            fun dsFun(q: String): Double {
                //for aaa
                if (q == "A") {
                    dsGpa = 4.0

                }
                if (q == "B+") {
                    dsGpa = 3.5

                }
                if (q == "B") {
                    dsGpa = 3.0

                }
                if (q == "C+") {
                    dsGpa = 2.5

                }
                if (q == "C") {
                    dsGpa = 2.0

                }
                if (q == "D+") {
                    dsGpa = 1.5

                }
                if (q == "D") {
                    dsGpa = 1.0

                }
                if (q == "E") {
                    dsGpa = 0.0
                }
                return dsGpa
            }

            fun dmbsFun(q: String): Double {

                //DBMS
                if (q == "A") {
                    DbmsGpa = 4.0
                }
                if (q == "B+") {
                    DbmsGpa = 3.5
                }
                if (q == "B") {
                    DbmsGpa = 3.0
                }
                if (q == "C+") {
                    DbmsGpa = 2.5
                }
                if (q == "C") {
                    DbmsGpa = 2.0
                }
                if (q == "D+") {
                    DbmsGpa = 1.5
                }
                if (q == "D") {
                    DbmsGpa = 1.0
                }
                if (q == "E") {
                    DbmsGpa = 0.0
                }
                return DbmsGpa
            }

            fun osFun(q: String): Double {
                //os
                if (q == "A") {
                    osGpa = 4.0
                }
                if (q == "B+") {
                    osGpa = 3.5
                }
                if (q == "B") {
                    osGpa = 3.0

                }
                if (q == "C+") {
                    osGpa = 2.5
                }
                if (q == "C") {
                    osGpa = 2.0
                }
                if (q == "D+") {
                    osGpa = 1.5
                }
                if (q == "D") {
                    osGpa = 1.0
                }
                if (q == "E") {
                    osGpa = 0.0
                }
                return osGpa
            }

            fun swFun(q: String): Double {
                if (q == "A") {
                    swGpa = 4.0
                }
                if (q == "B+") {
                    swGpa = 3.5
                }
                if (q == "B") {
                    swGpa = 3.0
                }
                if (q == "C+") {
                    swGpa = 2.5
                }
                if (q == "C") {
                    swGpa = 2.0
                }
                if (q == "D+") {
                    swGpa = 1.5
                }
                if (q == "D") {
                    swGpa = 1.0
                }
                if (q == "E") {
                    swGpa = 0.0
                }
                return swGpa
            }

            fun cpFun(q: String): Double {
                //CP
                if (q == "A") {
                    cpGpa = 4.0
                }
                if (q == "B+") {
                    cpGpa = 3.5
                }
                if (q == "B") {
                    cpGpa = 3.0
                }
                if (q == "C+") {
                    cpGpa = 2.5
                }
                if (q == "C") {
                    cpGpa = 2.0
                }
                if (q == "D+") {
                    cpGpa = 1.5
                }
                if (q == "D") {
                    cpGpa = 1.0
                }
                if (q == "E") {
                    cpGpa = 0.0
                }
                Log.d(d, "cp=  $cpGpa....sw==$swGpa")

                return cpGpa
            }


        })


    }


    // SHOW MENU ITEMS
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    // MOVE TO NEW ACTIVITY WHEN MENU ITEM IS SELECTED
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.mnu_Transcript -> {
                //  setContentView(R.layout.activity_transcript)
                this.startActivity(Intent(this, Transcript::class.java))
                return true
            }


            R.id.mnu_Calculator -> {
                // setContentView(R.layout.activity_gpa__calculator)
                this.startActivity(Intent(this, Gpa_Calculator::class.java))

                return true
            }
            R.id.mnu_Sign_Out -> {
                signOut()
                // setContentView(R.layout.activity_gpa__calculator)
                this.startActivity(Intent(this, VerifyUser::class.java))
                return true
            }
            R.id.mnu_Timeline -> {

                val currentUser = mAuth!!.currentUser
                updateUI(currentUser)
            }
            R.id.mnu_pastQuestions -> {
                this.startActivity(Intent(this, DashBoard::class.java))
                return true
            }

            else ->
                return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    fun signOut() {
        mAuth!!.signOut()
        finish()

    }

    private fun updateUI(currentUser: FirebaseUser?) {

        if (currentUser != null) {

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)

            startActivity(intent)

            finish()
        }

    }
}