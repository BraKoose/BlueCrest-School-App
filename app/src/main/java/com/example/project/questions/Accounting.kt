package com.example.project.questions

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.project.*
import com.example.project.R
import com.example.project.dashBoards.DashBoard
import com.example.project.databinding.ActivityAccountingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_accounting.*


class Accounting : AppCompatActivity() {
    private lateinit var binding: ActivityAccountingBinding
    private lateinit var mAuth: FirebaseAuth


    var total = 0
    var correct = 0
    private lateinit var reference: DatabaseReference
    var wrong = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()




        updateQuestion()
        reverseTimer(240, timerTxt)
    }

    private fun updateQuestion() {

        total++
        if (total > 10) {

            val i = Intent(this@Accounting, ResultActivity::class.java)
            i.putExtra("Total", total.toString())
            i.putExtra("Correct", correct.toString())
            i.putExtra("Incorrect", wrong.toString())
            startActivity(i)

            // open the result activity
        } else {
            reference =
                FirebaseDatabase.getInstance().reference.child("Accounting").child(total.toString())
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val question = dataSnapshot.getValue<Question>(Question::class.java)

                    binding.questionsTxt.text = question!!.getQuestion()
                    button1.text = question.getOption1()
                    button2.text = question.getOption2()
                    button3.text = question.getOption3()
                    button4.text = question.getOption4()


                  button1.setOnClickListener {
                      questionButtons(question)
                    }

                    button2.setOnClickListener {
                        questionButton2(question)
                    }


                    button3.setOnClickListener {
                        questionButton3(question)
                    }

                    button4.setOnClickListener {
                        questionButton4(question)
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        }
    }

    private fun questionButton4(question: Question) {
        if (button4.text.toString() == question.getAnswer()) {

            button4.setBackgroundColor(Color.GREEN)


            val handler = Handler()
            handler.postDelayed({
                correct++
                button4.setBackgroundColor(Color.parseColor("#03A9F4"))
                updateQuestion()
            }, 1500)
        } else {
            // answer is wrong... find correct answer an make it green

            wrong++
            button4.setBackgroundColor(Color.RED)

            if (button1.text.toString() == question.getAnswer()) {
                button1.setBackgroundColor(Color.GREEN)
            } else if (button2.text.toString() == question.getAnswer()) {
                button2.setBackgroundColor(Color.GREEN)
            } else if (button3.text.toString() == question.getAnswer()) {
                button3.setBackgroundColor(Color.GREEN)
            }

            val handler = Handler()
            handler.postDelayed(
                {
                    button1.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button2.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button3.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button4.setBackgroundColor(Color.parseColor("#03A9F4"))
                    updateQuestion()
                }, 1500
            )

        }
    }

    private fun questionButton3(question: Question) {
        if (button3.text.toString() == question.getAnswer()) {

            button3.setBackgroundColor(Color.GREEN)


            val handler = Handler()
            handler.postDelayed({
                correct++
                button3.setBackgroundColor(Color.parseColor("#03A9F4"))
                updateQuestion()
            }, 1500)
        } else {
            // answer is wrong... find correct answer an make it green

            wrong++
            button3.setBackgroundColor(Color.RED)

            when {
                button1.text.toString() == question.getAnswer() -> button1.setBackgroundColor(
                    Color.GREEN
                )
                button2.text.toString() == question.getAnswer() -> button2.setBackgroundColor(
                    Color.GREEN
                )
                button4.text.toString() == question.getAnswer() -> button4.setBackgroundColor(
                    Color.GREEN
                )
            }

            val handler = Handler()
            handler.postDelayed(
                {
                    button1.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button2.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button3.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button4.setBackgroundColor(Color.parseColor("#03A9F4"))
                    updateQuestion()
                }, 1500
            )

        }
    }

    private fun questionButton2(question: Question) {
        if (button2.text.toString() == question.getAnswer()) {

            button2.setBackgroundColor(Color.GREEN)


            val handler = Handler()
            handler.postDelayed({
                correct++
                button2.setBackgroundColor(Color.parseColor("#03A9F4"))
                updateQuestion()
            }, 1500)
        } else {
            // answer is wrong... find correct answer an make it green

            wrong++
            button2.setBackgroundColor(Color.RED)

            if (button1.text.toString() == question.getAnswer()) {
                button1.setBackgroundColor(Color.GREEN)
            } else if (button3.text.toString() == question.getAnswer()) {
                button3.setBackgroundColor(Color.GREEN)
            } else if (button4.text.toString() == question.getAnswer()) {
                button4.setBackgroundColor(Color.GREEN)
            }

            val handler = Handler()
            handler.postDelayed(
                {
                    button1.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button2.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button3.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button4.setBackgroundColor(Color.parseColor("#03A9F4"))
                    updateQuestion()
                }, 1500
            )

        }
    }

    private fun questionButtons(question: Question) {
        if (button1.text.toString() == question.getAnswer()) {

            button1.setBackgroundColor(Color.GREEN)


            val handler = Handler()
            handler.postDelayed({
                correct++
                button1.setBackgroundColor(Color.parseColor("#03A9F4"))
                updateQuestion()
            }, 1500)
        } else {
            // answer is wrong... find correct answer an make it green

            wrong++
            button1.setBackgroundColor(Color.RED)

            when {
                button2.text.toString() == question.getAnswer() -> button2.setBackgroundColor(
                    Color.GREEN
                )
                button3.text.toString() == question.getAnswer() -> button3.setBackgroundColor(
                    Color.GREEN
                )
                button4.text.toString() == question.getAnswer() -> button4.setBackgroundColor(
                    Color.GREEN
                )
            }

            val handler = Handler()
            handler.postDelayed(
                {
                    button1.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button2.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button3.setBackgroundColor(Color.parseColor("#03A9F4"))
                    button4.setBackgroundColor(Color.parseColor("#03A9F4"))
                    updateQuestion()
                }, 1500
            )

        }
    }

    private fun reverseTimer(seconds: Int, tv: TextView) {

        object : CountDownTimer((seconds * 1000 + 1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var seconds = millisUntilFinished.toInt() / 1000
                val minutes = seconds / 60
                seconds %= 60

                tv.text = ("${String.format("%02d", minutes)}:${String.format("%02d", seconds)}")
            }

            override fun onFinish() {
                tv.text = getString(R.string.complte)
                val myIntent = Intent(this@Accounting, ResultActivity::class.java)
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

    private val toTranscript: Boolean
        get() {
            this.startActivity(Intent(this, Transcript::class.java))
            return true
        }

    private val toCalculatorGPa: Boolean
        get() {
            this.startActivity(Intent(this, GpaCalculator::class.java))
            return true
        }

    private val signOut: Boolean
        get() {
            this.startActivity(Intent(this, VerifyUser::class.java))
            return true
        }

    private val toTimeline: Boolean
        get() {
            val currentUser = mAuth.currentUser
            updateUI(currentUser)
            return true
        }

    private val toPastCo: Boolean
        get() {
            this.startActivity(Intent(this, DashBoard::class.java))
            return true
        }

    // MOVE TO NEW ACTIVITY WHEN MENU ITEM IS SELECTED
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnu_Transcript -> {
                //  setContentView(R.layout.activity_transcript)
                return toTranscript
            }


            R.id.mnu_Calculator -> {
                // setContentView(R.layout.activity_gpa__calculator)
                return toCalculatorGPa
            }
            R.id.mnu_Sign_Out -> {
                signOut()
                // setContentView(R.layout.activity_gpa__calculator)
                return signOut
            }
            R.id.mnu_Timeline ->{
//                this.startActivity(Intent(this,MainActivity::class.java))
                return toTimeline
            }
            R.id.mnu_pastQuestions -> {
                return toPastCo
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
