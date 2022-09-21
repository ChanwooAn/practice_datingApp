package com.example.datingapppractice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import cardstackview.CardStackLayoutManager
import cardstackview.CardStackListener
import cardstackview.Direction
import com.example.datingapppractice.auth.IntroActivity
import com.example.datingapppractice.model.User
import com.example.datingapppractice.slding.CardStackAdapter
import com.example.datingapppractice.util.FirebaseUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_my_page.*

private const val TAG="MainActivity"
class MainActivity : AppCompatActivity() {

    lateinit var  cardStackAdapter: CardStackAdapter
    lateinit var manager:CardStackLayoutManager
    lateinit var currentUser:User
    private var userList= mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_logoutBtn.setOnClickListener{ it ->
            val popupMenu=PopupMenu(this,it)
            popupMenu.menuInflater.inflate(R.menu.main_page_popup,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {menu->
                when(menu?.itemId){
                    R.id.mainPopup_menu1-> {
                        startActivity(Intent(this,MyPageActivity::class.java))
                        true
                    }

                    R.id.mainPopup_menu2-> {
                        FirebaseAuth.getInstance().signOut()
                        startActivity(Intent(this,IntroActivity::class.java))
                        true
                    }
                    else->false
                }

            }

            popupMenu.show()

        }

        var swipeCount=0
        manager= CardStackLayoutManager(baseContext,object:CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardSwiped(direction: Direction?) {

                swipeCount++
                if(swipeCount==cardStackAdapter.itemCount){
                    userList= mutableListOf()
                    getUserData()
                    swipeCount=0

                }

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

        getUserData()
        cardStackAdapter=CardStackAdapter(baseContext,userList)
        card_stack_view.layoutManager=manager
        card_stack_view.adapter=cardStackAdapter



    }
    private fun getUserData() {

/*
          userList= mutableListOf()
*/

          FirebaseUtil.userInfoRef.child(FirebaseUtil.getUid()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                currentUser = dataSnapshot.getValue(User::class.java) ?: User()
                Log.d(TAG,currentUser.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })//현재 userdata받아오기


        FirebaseUtil.userInfoRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(e in dataSnapshot.children){
                    val user=e.getValue(User::class.java)
                    Log.d(TAG,user.toString())
                    if(user!!.gender==currentUser.gender){
                        continue
                    }
                    else{
                        userList.add(user)
                    }
                }
                cardStackAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        Log.d(TAG,userList.size.toString())
    }



}