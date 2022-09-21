package com.example.datingapppractice.slding

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datingapppractice.R
import com.example.datingapppractice.model.User
import com.example.datingapppractice.util.FirebaseUtil
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.activity_my_page.view.*
import kotlinx.android.synthetic.main.item_card.*
import kotlinx.android.synthetic.main.item_card.view.*

private const val TAG="CardStackAdapter"
class CardStackAdapter(val context: Context, val items:List<User>): RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        @SuppressLint("SetTextI18n")
        fun binding(data:User){
            itemView.card_name.text = "Name : ${data.nickName}"
            itemView.card_age.text="Age: ${data.age}"
            itemView.card_locale.text="locale: ${data.locale}"

            FirebaseUtil.storage.child("profileImage/${data.uid}.png").downloadUrl.addOnSuccessListener {
                // Got the download URL for 'users/me/profile.png'
                Glide.with(context).load(it?:R.drawable.profile_img).into(itemView.card_profile)
            }.addOnFailureListener {
                // Handle any errors
                Log.d(TAG,"fail")
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view: View =inflater.inflate(R.layout.item_card,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        holder.binding(items[position])
        Log.d(TAG,"onBindViewHolder")

    }

}