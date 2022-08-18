package com.example.datingapppractice.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FirebaseUtil {


    companion object{

        private lateinit var auth : FirebaseAuth

        fun getUid() : String {

            auth = FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString()

        }

        val database = Firebase.database

        val userInfoRef = database.getReference("userInfo")
        val userLikeRef = database.getReference("userLike")

        val storage = Firebase.storage.reference

    }


}