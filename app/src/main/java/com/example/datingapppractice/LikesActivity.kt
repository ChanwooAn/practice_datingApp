package com.example.datingapppractice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datingapppractice.model.User
import com.example.datingapppractice.util.FirebaseUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_likes.*
import kotlinx.android.synthetic.main.item_likes.view.*

class LikesActivity : AppCompatActivity() {

    lateinit var likesRecycler:RecyclerView


    inner class LikesRecyclerAdapter(var people:MutableList<User>):
        RecyclerView.Adapter<LikesRecyclerAdapter.LikesViewHolder>() {



        inner class LikesViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView),View.OnClickListener {
            private val name:TextView=itemView.name_likes
            private val gender:TextView=itemView.gender_likes
            private var uid:String=""


            init{
                itemView.setOnClickListener(this)
            }

            override fun onClick(p0: View?) {
                // 나를 좋아요 했는지 표시하기
               FirebaseUtil.userInfoRef.child(uid).get().addOnSuccessListener {

                    for(e in it.child("Like").children){
                        if(e.key==FirebaseUtil.getUid()){
                            Toast.makeText(applicationContext,"likes me too",Toast.LENGTH_SHORT).show()

                        }
                    }
                }.addOnFailureListener{
                    Log.e("firebase", "Error getting data", it)
                }

            }

            fun bind(user:User){
                name.text=user.nickName
                gender.text=user.gender
                uid=user.uid
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikesViewHolder {
            val view=LayoutInflater.from(baseContext).inflate(R.layout.item_likes,parent,false)

            return LikesViewHolder(view)
        }

        override fun onBindViewHolder(holder: LikesViewHolder, position: Int) {
            Log.d("LikesViewHolder","onBind")
            holder.bind(people[position])
        }

        override fun getItemCount(): Int {
            return people.size
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_likes)
        likesRecycler=findViewById(R.id.recycler_likes)
        Log.d("LikesActivity",FirebaseUtil.getUid())

        likesRecycler.layoutManager=LinearLayoutManager(this)
        updateUI()


    }






    private fun updateUI(){
        val uids= mutableListOf<String>()

        val postListener=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(datamodel in snapshot.children){
                    uids.add(datamodel.key.toString())
                }
                Log.d("LikesActivity",uids.toString())
                getLikesUserData(uids)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        FirebaseUtil.userInfoRef.child(FirebaseUtil.getUid()).child("Like").addValueEventListener(postListener)




    }
    fun getLikesUserData(people:MutableList<String>): MutableList<User>{
        val ret= mutableListOf<User>()
        FirebaseUtil.userInfoRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(i in dataSnapshot.children){
                    val uid=i.child("uid")
                    if(people.contains(uid.value.toString())){
                        val user=User(
                            i.child("uid").value.toString(),
                            "",
                            "",
                            i.child("nickName").value.toString(),
                            i.child("age").value.toString(),
                            i.child("gender").value.toString(),
                            i.child("locale").value.toString()
                        )
                        ret.add(user)
                    }

                    recycler_likes.adapter=LikesRecyclerAdapter(ret)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        return ret
    }



}