package com.example.project

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_login.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class Login : AppCompatActivity(){

    private lateinit var mAuth: FirebaseAuth

    //private val mStorageRef: StorageReference? = null


    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()



        ivImagePerson.setOnClickListener {
            checkPermission()
        }

        but_Login.setOnClickListener {
            startActivity(Intent(this, VerifyUser::class.java))

        }
    }

    fun LoginToFireBase(email: String, password: String) {

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Successful Login ", Toast.LENGTH_LONG)
                        .show()

                    SaveImageInFirebase()


                } else {
                    Toast.makeText(applicationContext, "failed to login", Toast.LENGTH_LONG).show()

                }
            }

    }


    fun SaveImageInFirebase() {
        var currentUser = mAuth!!.currentUser
        val email: String = currentUser!!.email.toString()
        val storage = FirebaseStorage.getInstance()
        val storgaRef = storage.getReferenceFromUrl("gs://pro-book-72bf7.appspot.com/")
        val df = SimpleDateFormat("ddMMyyHHmmss")
        val dataobj = Date()
        val imagePath = SplitString(email) + "." + df.format(dataobj) + ".jpg"
        val ImageRef = storgaRef.child("images/" + imagePath)
        ivImagePerson.isDrawingCacheEnabled = true
        ivImagePerson.buildDrawingCache()
        val drawable = ivImagePerson.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()


        val uploadTask = ImageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(applicationContext, "fail to upload", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener { taskSnapshot ->

            ImageRef.downloadUrl.addOnCompleteListener() { task ->

                var DownloadURL = task.result.toString()



                myRef.child("Users").child(currentUser.uid).child("email")
                    .setValue(currentUser.email)
                myRef.child("Users").child(currentUser.uid).child("ProfileImage")
                    .setValue(DownloadURL)
                LoadTweets()
            }

        }
    }

    fun SplitString(email: String): String {
        val split = email.split("@")
        return split[0]
    }

    override fun onStart() {
        super.onStart()
        LoadTweets()
    }

    fun LoadTweets() {
        var currentUser = mAuth!!.currentUser

        if (currentUser != null) {


            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)

            startActivity(intent)
        }
    }

    val READIMAGE: Int = 253
    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    READIMAGE
                )
                return
            }
        }
        loadImage()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READIMAGE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImage()
                } else {
                    Toast.makeText(this, "Can not access your images", Toast.LENGTH_LONG).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }

    val PICK_IMAGE_CODE = 123
    fun loadImage() {
        var intent = Intent(
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
            ivImagePerson.setImageBitmap(BitmapFactory.decodeFile(picturePath))
        }
    }


    fun buLogin(view: View) {
        LoginToFireBase(etEmail.text.toString(), etPassword.text.toString())

    }

}
