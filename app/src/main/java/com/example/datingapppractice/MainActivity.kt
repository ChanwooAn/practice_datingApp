package com.example.datingapppractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cardstackview.CardStackLayoutManager
import cardstackview.CardStackListener
import cardstackview.CardStackView
import cardstackview.Direction
import com.example.datingapppractice.slding.CardStackAdapter

class MainActivity : AppCompatActivity() {

    lateinit var  cardStackAdapter: CardStackAdapter
    lateinit var manager:CardStackLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cardStackView = findViewById<CardStackView>(R.id.card_stack_view)

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
        cardStackView.layoutManager=manager
        cardStackView.adapter=cardStackAdapter
    }
}