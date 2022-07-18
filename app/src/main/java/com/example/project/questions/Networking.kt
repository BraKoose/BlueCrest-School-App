package com.example.project.questions

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project.*
import com.example.project.R
import com.example.project.dashBoards.DashBoard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


private var mAuth: FirebaseAuth? = null

class Networking : AppCompatActivity() {

    lateinit var b1: Button
    lateinit var b2: Button
    lateinit var b3: Button
    lateinit var b4: Button
    lateinit var t1_question: TextView
    lateinit var timerTxt: TextView
    var total = 0
    var correct = 0
    lateinit var reference: DatabaseReference
    var wrong = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_networking)
        mAuth = FirebaseAuth.getInstance()




        b1 = findViewById(R.id.button1)
        b2 = findViewById(R.id.button2)
        b3 = findViewById(R.id.button3)
        b4 = findViewById(R.id.button4)


        t1_question = findViewById(R.id.questionsTxt)
        timerTxt = findViewById(R.id.timerTxt)


        updateQuestion()
        reverseTimer(240, timerTxt)
    }

    private fun updateQuestion() {

        total++
        if (total > 10) {

            val i = Intent(this@Networking, ResultActivity::class.java)
            i.putExtra("Total", total.toString())
            i.putExtra("Correct", correct.toString())
            i.putExtra("Incorrect", wrong.toString())
            startActivity(i)

            // open the result activity
        } else {
            reference =
                FirebaseDatabase.getInstance().reference.child("networking").child(total.toString())
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val question = dataSnapshot.getValue<Question>(Question::class.java)

                    t1_question.text = question!!.getQuestion()
                    b1.text = question.getOption1()
                    b2.text = question.getOption2()
                    b3.text = question.getOption3()
                    b4.text = question.getOption4()


                    b1.setOnClickListener {
                        if (b1.text.toString() == question.getAnswer()) {

                            b1.setBackgroundColor(Color.GREEN)


                            val handler = Handler()
                            handler.postDelayed({
                                correct++
                                b1.setBackgroundColor(Color.parseColor("#03A9F4"))
                                updateQuestion()
                            }, 1500)
                        } else {
                            // answer is wrong... find correct answer an make it green

                            wrong++
                            b1.setBackgroundColor(Color.RED)

                            when {
                                b2.text.toString() == question.getAnswer() -> b2.setBackgroundColor(
                                    Color.GREEN
                                )
                                b3.text.toString() == question.getAnswer() -> b3.setBackgroundColor(
                                    Color.GREEN
                                )
                                b4.text.toString() == question.getAnswer() -> b4.setBackgroundColor(
                                    Color.GREEN
                                )
                            }

                            val handler = Handler()
                            handler.postDelayed(
                                {
                                    b1.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b2.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b3.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b4.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    updateQuestion()
                                }, 1500
                            )

                        }
                    }

                    b2.setOnClickListener {
                        if (b2.text.toString() == question.getAnswer()) {

                            b2.setBackgroundColor(Color.GREEN)


                            val handler = Handler()
                            handler.postDelayed({
                                correct++
                                b2.setBackgroundColor(Color.parseColor("#03A9F4"))
                                updateQuestion()
                            }, 1500)
                        } else {
                            // answer is wrong... find correct answer an make it green

                            wrong++
                            b2.setBackgroundColor(Color.RED)

                            when {
                                b1.text.toString() == question.getAnswer() -> b1.setBackgroundColor(
                                    Color.GREEN
                                )
                                b3.text.toString() == question.getAnswer() -> b3.setBackgroundColor(
                                    Color.GREEN
                                )
                                b4.text.toString() == question.getAnswer() -> b4.setBackgroundColor(
                                    Color.GREEN
                                )
                            }

                            val handler = Handler()
                            handler.postDelayed(
                                {
                                    b1.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b2.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b3.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b4.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    updateQuestion()
                                }, 1500
                            )

                        }
                    }


                    b3.setOnClickListener {
                        if (b3.text.toString() == question.getAnswer()) {

                            b3.setBackgroundColor(Color.GREEN)


                            val handler = Handler()
                            handler.postDelayed({
                                correct++
                                b3.setBackgroundColor(Color.parseColor("#03A9F4"))
                                updateQuestion()
                            }, 1500)
                        } else {
                            // answer is wrong... find correct answer an make it green

                            wrong++
                            b3.setBackgroundColor(Color.RED)

                            when {
                                b1.text.toString() == question.getAnswer() -> b1.setBackgroundColor(
                                    Color.GREEN
                                )
                                b2.text.toString() == question.getAnswer() -> b2.setBackgroundColor(
                                    Color.GREEN
                                )
                                b4.text.toString() == question.getAnswer() -> b4.setBackgroundColor(
                                    Color.GREEN
                                )
                            }

                            val handler = Handler()
                            handler.postDelayed(
                                {
                                    b1.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b2.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b3.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b4.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    updateQuestion()
                                }, 1500
                            )

                        }
                    }

                    b4.setOnClickListener {
                        if (b4.text.toString() == question.getAnswer()) {

                            b4.setBackgroundColor(Color.GREEN)


                            val handler = Handler()
                            handler.postDelayed({
                                correct++
                                b4.setBackgroundColor(Color.parseColor("#03A9F4"))
                                updateQuestion()
                            }, 1500)
                        } else {
                            // answer is wrong... find correct answer an make it green

                            wrong++
                            b4.setBackgroundColor(Color.RED)

                            when {
                                b1.text.toString() == question.getAnswer() -> b1.setBackgroundColor(
                                    Color.GREEN
                                )
                                b2.text.toString() == question.getAnswer() -> b2.setBackgroundColor(
                                    Color.GREEN
                                )
                                b3.text.toString() == question.getAnswer() -> b3.setBackgroundColor(
                                    Color.GREEN
                                )
                            }

                            val handler = Handler()
                            handler.postDelayed(
                                {
                                    b1.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b2.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b3.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    b4.setBackgroundColor(Color.parseColor("#03A9F4"))
                                    updateQuestion()
                                }, 1500
                            )

                        }
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        }
    }

    fun reverseTimer(seconds: Int, tv: TextView) {

        object : CountDownTimer((seconds * 1000 + 1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var seconds = millisUntilFinished.toInt() / 1000
                val minutes = seconds / 60
                seconds = seconds % 60

                tv.text = (String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds))
            }

            override fun onFinish() {
                tv.text = "Completed"
                val myIntent = Intent(this@Networking, ResultActivity::class.java)
                myIntent.putExtra("Total", total.toString())
                myIntent.putExtra("Correct", correct.toString())
                myIntent.putExtra("Incorrect", wrong.toString())
                startActivity(myIntent)
            }
        }.start()
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
//                this.startActivity(Intent(this,MainActivity::class.java))
                val currentUser = mAuth!!.currentUser
                updateUI(currentUser)
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
        } else {
//            Toast.makeText(applicationContext, "Authentication failed.",
//                Toast.LENGTH_SHORT).show();
        }

    }

}


