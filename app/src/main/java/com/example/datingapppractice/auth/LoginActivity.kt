package com.example.datingapppractice.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.datingapppractice.MainActivity
import com.example.datingapppractice.R
import com.example.datingapppractice.util.FirebaseUtil
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*


private const val TAG="LoginActivity"

class LoginActivity : AppCompatActivity() {


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        login_pwSeeBtn.setOnTouchListener { _, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> login_pw.inputType=InputType.TYPE_CLASS_TEXT
                MotionEvent.ACTION_MOVE -> {login_pw.inputType=InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD}
                MotionEvent.ACTION_UP -> {login_pw.inputType=InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD}
                MotionEvent.ACTION_CANCEL -> {login_pw.inputType=InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD}

            }

            true
        }


        val auth=FirebaseAuth.getInstance()
        login_loginBtn.setOnClickListener{
            auth.signInWithEmailAndPassword(login_id.text.toString(),login_pw.text.toString() )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser

                        startActivity(Intent(this,MainActivity::class.java).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK ))
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