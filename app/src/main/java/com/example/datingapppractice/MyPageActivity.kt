package com.example.datingapppractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.datingapppractice.model.User
import com.example.datingapppractice.util.FirebaseUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_my_page.*

private const val TAG="MyPageActivity"

class MyPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        FirebaseUtil.storage.child("profileImage/${FirebaseUtil.getUid()}.png").downloadUrl.addOnSuccessListener {
            // Got the download URL for 'users/me/profile.png'
            Glide.with(this).load(it?:R.drawable.profile_img).into(myPage_profileImg)
        }.addOnFailureListener {
            // Handle any errors
            Log.d(TAG,"fail")
        }//프로필사진 다운

        // Read from the database
        FirebaseUtil.userInfoRef.child(FirebaseUtil.getUid()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(User::class.java)

                myPage_ageInput.text= value?.age
                myPage_emailInput.text=value?.email
                myPage_genderInput.text=value?.gender
                myPage_nickNameInput.text=value?.nickName
                myPage_localeInput.text=value?.locale


                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })



    }
}