package com.example.project

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

    var ListTweets = ArrayList<Ticket>()
    var adapter: MyTweetAdapter? = null
    var myemail: String? = null
    var UserUID: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()


        var b: Bundle = intent.extras!!
        myemail = b.getString("email")
        UserUID = b.getString("uid")

        //Dummy data
        ListTweets.add(Ticket("0", "hey", "url", "add"))


        adapter = MyTweetAdapter(this, ListTweets)
        lvTweets.adapter = adapter

        LoadPost()
    }


    // SHOW MENU ITEMS
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    // MOVE TO NEW ACTIVITY WHEN MENU ITEM IS SELECTED
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId){
            R.id.mnu_Transcript -> {
                //  setContentView(R.layout.activity_transcript)
                this.startActivity(Intent(this, Transcript::class.java))
                return true
            }

            R.id.mnu_Calculator ->{
               // setContentView(R.layout.activity_gpa__calculator)
                this.startActivity(Intent(this, Gpa_Calculator::class.java))

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

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val myTweet = listNoteAdapter[position]

            if (myTweet.tweetPersonUID.equals("add")) {
                val myView = layoutInflater.inflate(R.layout.add_ticket, null)
                myView.iv_attach.setOnClickListener(View.OnClickListener {
                    loadImage()
                })
                myView.iv_post.setOnClickListener(View.OnClickListener {
                    //upload to the server

                    myRef.child("posts").push().setValue(
                        PostInfo(
                            UserUID!!,
                            myView.etPost.text.toString(), DownloadURL!!
                        )
                    )

                    myView.etPost.setText("")

                })
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
                                        Picasso.get().load(userInfo).into(myView.picture_path);

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
    private val PICK_IMAGE_CODE = 123

    fun loadImage() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, PICK_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_CODE && data != null && resultCode == RESULT_OK) {
            val selectedImage = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            UploadImage(BitmapFactory.decodeFile(picturePath))
        }

    }

    var DownloadURL: String? = ""

    fun UploadImage(bitmap: Bitmap) {

        ListTweets.add(0, Ticket("0", "him", "url", "loading"))
        adapter!!.notifyDataSetChanged()

        val storage = FirebaseStorage.getInstance()
        val storgaRef = storage.getReferenceFromUrl("gs://pro-book-72bf7.appspot.com/")
        val df = SimpleDateFormat("ddMMyyHHmmss")
        val dataobj = Date()
        val imagePath = SplitString(myemail!!) + "." + df.format(dataobj) + ".jpg"
        val ImageRef = storgaRef.child("imagesPost/$imagePath")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = ImageRef.putBytes(data)



        uploadTask.addOnFailureListener {
            Toast.makeText(applicationContext, "fail to upload", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener { task ->

            ImageRef.downloadUrl.addOnCompleteListener(){task ->

                DownloadURL=task.result.toString()

                Log.d("download url:", "$DownloadURL")

                ListTweets.removeAt(0)
                adapter!!.notifyDataSetChanged()
            }



       }

    }
    

    fun SplitString(email: String): String {
        val split = email.split("@")
        return split[0]
    }


    fun LoadPost() {

        myRef.child("posts")
        .addValueEventListener(object :ValueEventListener{

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                try {

                    ListTweets.clear()
                    ListTweets.add(Ticket("0","him","url","add"))

                    var td= dataSnapshot!!.value as HashMap<*, *>

                    for(key in td.keys){

                        val post= td[key] as HashMap<*, *>

                        ListTweets.add(Ticket(
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


