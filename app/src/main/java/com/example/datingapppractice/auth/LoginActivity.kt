package com.example.datingapppractice.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.datingapppractice.MainActivity
import com.example.datingapppractice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


const private val TAG="LoginActivity"
class LoginActivity : AppCompatActivity() {

    lateinit private var auth:FirebaseAuth
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth= Firebase.auth


        /*login_pwSeeBtn.setOnTouchListener { _, motionEvent ->
            when(motionEvent){
               MotionEvent.ACTION_DOWN->
            }
        }*/
        login_loginBtn.setOnClickListener{
            auth.signInWithEmailAndPassword(login_id.text.toString(),login_pw.text.toString() )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser

                        startActivity(Intent(this,MainActivity::class.java))
                        //updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed : "+task.exception,
                            Toast.LENGTH_LONG).show()
                        //updateUI(null)
                    }
                }

        }

    }


}