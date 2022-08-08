package com.example.datingapppractice.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.datingapppractice.MainActivity
import com.example.datingapppractice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.activity_join.*


private const val TAG="JoinActivity"
class JoinActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth=Firebase.auth
        join_joinBtn.setOnClickListener {
            auth.createUserWithEmailAndPassword(join_email.text.toString(), join_pw.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Log.d(TAG, user?.email.toString())

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