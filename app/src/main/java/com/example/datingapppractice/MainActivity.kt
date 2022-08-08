package com.example.datingapppractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cardstackview.CardStackLayoutManager
import cardstackview.CardStackListener
import cardstackview.Direction
import com.example.datingapppractice.auth.IntroActivity
import com.example.datingapppractice.slding.CardStackAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var  cardStackAdapter: CardStackAdapter
    lateinit var manager:CardStackLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_logoutBtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,IntroActivity::class.java))
        }

        manager= CardStackLayoutManager(baseContext,object:CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction?) {

            }
            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {
            }


        })
        val testList= listOf<String>("a","b","c","d")
        cardStackAdapter=CardStackAdapter(baseContext,testList)
        card_stack_view.layoutManager=manager
        card_stack_view.adapter=cardStackAdapter
    }
}