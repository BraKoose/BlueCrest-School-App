package com.example.project

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project.dashBoards.DashBoard
import com.example.project.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_ticket.view.*
import kotlinx.android.synthetic.main.tweets_ticket.view.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private var mAuth: FirebaseAuth? = null

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    var listTweets = ArrayList<Ticket>()
    var adapter: MyTweetAdapter? = null
    var myemail: String? = null
    var userUID: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()


        val b: Bundle = intent.extras!!
        myemail = b.getString("email")
        userUID = b.getString("uid")

        //Dummy data
        listTweets.add(Ticket("0", "hey", "url", "add"))


        adapter = MyTweetAdapter(this, listTweets)
        lvTweets.adapter = adapter

        loadPost()
    }


    // SHOW MENU ITEMS
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    // MOVE TO NEW ACTIVITY WHEN MENU ITEM IS SELECTED
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.mnu_Transcript -> {
                //  setContentView(R.layout.activity_transcript)
                this.startActivity(Intent(this, Transcript::class.java))
                return true
            }

            R.id.mnu_Calculator ->{
               // setContentView(R.layout.activity_gpa__calculator)
                this.startActivity(Intent(this, GpaCalculator::class.java))

            return true
            }
            R.id.mnu_Sign_Out ->{
                signOut()
                // setContentView(R.layout.activity_gpa__calculator)
                this.startActivity(Intent(this, VerifyUser::class.java))
                return true
            }

            R.id.mnu_pastQuestions ->{
                this.startActivity(Intent(this, DashBoard::class.java))
                return true
            }
            R.id.mnu_Timeline ->{
//                this.startActivity(Intent(this,MainActivity::class.java))
//                return true
                val currentUser = mAuth!!.currentUser
                updateUI(currentUser )
            }

            else ->
                return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    inner class MyTweetAdapter(context: Context, var listNoteAdapter: ArrayList<Ticket>) :
        BaseAdapter() {
        var context: Context? = context

        @SuppressLint("InflateParams")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val myTweet = listNoteAdapter[position]

            if (myTweet.tweetPersonUID.equals("add")) {

                val myView = layoutInflater.inflate(R.layout.add_ticket, null)
                myView.iv_attach.setOnClickListener {
                    loadImage()
                }
                myView.iv_post.setOnClickListener {
                    //upload to the server

                    myRef.child("posts").push().setValue(
                        PostInfo(
                            userUID!!,
                            myView.etPost.text.toString(), downloadURL!!
                        )
                    )

                    myView.etPost.setText("")

                }
                return myView
            }
            else if (myTweet.tweetPersonUID.equals("loading")) {
                return layoutInflater.inflate(R.layout.loading_ticket, null)

            }
            else {
                val myView = layoutInflater.inflate(R.layout.tweets_ticket, null)
                myView.txt_tweet.text = myTweet.tweetText

                //  myView.tweet_picture.setImageURI(myTweet.tweetImageURL)

                //Picasso.with(context).load(myTweet.tweetImageURL).into(myView.tweet_picture)

                Picasso.get().load(myTweet.tweetImageURL).into(myView.tweet_picture)



                myRef.child("Users").child(myTweet.tweetPersonUID!!)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            try {

                                val td = dataSnapshot.value as HashMap<*, *>

                                for (key in td.keys) {
                                    val userInfo = td[key] as String
                                    if (key.equals("ProfileImage") ) {

                                       // Picasso.with(context).load(userInfo).into(myView.picture_path)
                                        Picasso.get().load(userInfo).into(myView.picture_path)

                                        Log.d("profile image URL :", userInfo)
                                    } else {
                                        myView.txtUserName.text = userInfo
                                    }
                                }

                            } catch (ex: Exception) {
                            }
                        }

                        override fun onCancelled(p0: DatabaseError) {

                        }

                    })

                return myView
            }


        }

        override fun getItem(position: Int): Any {

            return listNoteAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listNoteAdapter.size
        }
    }

    // For Loading Image
    private val PICKIMAGECODE = 123

    fun loadImage() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, PICKIMAGECODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICKIMAGECODE && data != null && resultCode == RESULT_OK) {
            val selectedImage = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            uploadImage(BitmapFactory.decodeFile(picturePath))
        }

    }

    var downloadURL: String? = ""

    @SuppressLint("SimpleDateFormat")
    fun uploadImage(bitmap: Bitmap) {

        listTweets.add(0, Ticket("0", "him", "url", "loading"))
        adapter!!.notifyDataSetChanged()

        val storage = FirebaseStorage.getInstance()
        val storgaRef = storage.getReferenceFromUrl("gs://pro-book-72bf7.appspot.com/")
        val df = SimpleDateFormat("ddMMyyHHmmss")
        val dataobj = Date()
        val imagePath = splitString(myemail!!) + "." + df.format(dataobj) + ".jpg"
        val imageRef = storgaRef.child("imagesPost/$imagePath")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)



        uploadTask.addOnFailureListener {
            Toast.makeText(applicationContext, "fail to upload", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {

            imageRef.downloadUrl.addOnCompleteListener(){ task ->

                downloadURL=task.result.toString()

                Log.d("download url:", "$downloadURL")

                listTweets.removeAt(0)
                adapter!!.notifyDataSetChanged()
            }



       }

    }
    

    private fun splitString(email: String): String {
        val split = email.split("@")
        return split[0]
    }


    private fun loadPost() {

        myRef.child("posts")
        .addValueEventListener(object :ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    listTweets.clear()
                    listTweets.add(Ticket("0","him","url","add"))

                    val td= dataSnapshot.value as HashMap<*, *>

                    for(key in td.keys){

                        val post= td[key] as HashMap<*, *>

                        listTweets.add(Ticket(
                            key as String,

                            post["text"] as String,
                            post["postImage"] as String
                            ,post["userUID"] as String))


                    }


                    adapter!!.notifyDataSetChanged()
                }catch (ex:Exception){}


            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
    fun signOut(){
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


