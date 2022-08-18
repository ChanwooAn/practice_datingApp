package com.example.datingapppractice.auth

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.datingapppractice.MainActivity
import com.example.datingapppractice.R
import com.example.datingapppractice.model.User
import com.example.datingapppractice.util.FirebaseUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_join.*
import java.io.ByteArrayOutputStream
import java.io.File


private const val TAG="JoinActivity"
class JoinActivity : AppCompatActivity() {



    lateinit var profileUri: Uri
    private var imgFlag=false

    fun uploadPhoto(fileName:String){
        // Get the data from an ImageView as bytes
        join_profileImage.isDrawingCacheEnabled = true
        join_profileImage.buildDrawingCache()
        val bitmap = (join_profileImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = FirebaseUtil.storage.child("/profileImage/${fileName}.png").putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)





        val getAction=registerForActivityResult(ActivityResultContracts.GetContent()){uri->
            join_profileImage.setImageURI(uri)
            profileUri=uri
        }
        join_profileImage.setOnClickListener {
            getAction.launch("image/*")
            imgFlag=true
        }



        join_joinBtn.setOnClickListener {
            //uid / emain id / pw / nickname / age / gender / locale
            val user= User("",
                join_email.text.toString(),
                join_pw.text.toString(),
                join_nickName.text.toString(),
                join_age.text.toString(),
                join_gender.text.toString(),
                join_locale.text.toString()
            )



            val auth=FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(user.email, user.pw)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val currentUser = auth.currentUser

                        user.uid=currentUser?.uid.toString()
                        Log.d(TAG, currentUser?.email.toString())

                        uploadPhoto(user.uid)
                        FirebaseUtil.userInfoRef.child(user.uid).setValue(user)

                        auth.signOut()
                        startActivity(Intent(this,IntroActivity::class.java))

                        // updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed : "+task.exception,
                            Toast.LENGTH_LONG
                        ).show()
                        // updateUI(null)
                    }
                }






        }




    }


















}