package com.example.datingapppractice.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.datingapppractice.R
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        joinBtn.setOnClickListener{
            startActivity(Intent(this,JoinActivity::class.java))
        }


        loginBtn.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }


      /*  val joinBtn=findViewById<Button>(R.id.joinBtn)
        joinBtn.setOnClickListener({
            val intent= Intent(this,JoinActivity::class.java)
            startActivity(intent)
        })*/
    }
}