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
import com.example.project.databinding.ActivityDiscreteMathsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*



class DiscreteMaths : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding:ActivityDiscreteMathsBinding

    private var b1 = binding.button1
    private var b2 = binding.button2
    private var b3 = binding.button3
    private var b4 = binding.button4
    //
//
    var t1Question = binding.questionsTxt
    var total = 0
    var correct = 0
    lateinit var reference: DatabaseReference
    var wrong = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscreteMathsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()




        t1Question = binding.questionsTxt
        val timerTxt = binding.timerTxt

        updateQuestion()
        reverseTimer(240, timerTxt)
    }

    private fun updateQuestion() {

        total++
        if (total > 10) {

            val i = Intent(this@DiscreteMaths, ResultActivity::class.java)
            i.putExtra("Total", total.toString())
            i.putExtra("Correct", correct.toString())
            i.putExtra("Incorrect", wrong.toString())
            startActivity(i)

            // open the result activity
        } else {
            reference =
                FirebaseDatabase.getInstance().reference.child("Discrete").child(total.toString())
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val question = dataSnapshot.getValue<Question>(Question::class.java)

                    t1Question.text = question!!.getQuestion()
                    b1.text = question.getOption1()
                    b2.text = question.getOption2()
                    b3.text = question.getOption3()
                    b4.text = question.getOption4()


                    b1.setOnClickListener {
                        button1(question)
                    }

                    b2.setOnClickListener {
                        button2(question)
                    }


                    b3.setOnClickListener {
                        button3(question)
                    }

                    b4.setOnClickListener {
                        button4(question)
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })

        }
    }

    private fun button4(question: Question) {
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

    private fun button3(question: Question) {
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

    private fun button2(question: Question) {
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

    private fun button1(question: Question) {
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

    fun reverseTimer(seconds: Int, tv: TextView) {

        object : CountDownTimer((seconds * 1000 + 1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var seconds = millisUntilFinished.toInt() / 1000
                val minutes = seconds / 60
                seconds %= 60

                tv.text = ("${String.format("%02d", minutes)}:${String.format("%02d", seconds)}")
            }

            override fun onFinish() {
                tv.text = getString(R.string.zzs)
                val myIntent = Intent(this@DiscreteMaths, ResultActivity::class.java)
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

    private val toCalulator: Boolean
        get() {
            this.startActivity(Intent(this, GpaCalculator::class.java))
            return true
        }

    private val verifyUser: Boolean
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
                return toCalulator
            }
            R.id.mnu_Sign_Out -> {
                signOut()
                // setContentView(R.layout.activity_gpa__calculator)
                return verifyUser
            }
            R.id.mnu_Timeline -> {
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


