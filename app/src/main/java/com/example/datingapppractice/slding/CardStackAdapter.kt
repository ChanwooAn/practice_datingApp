package com.example.datingapppractice.slding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datingapppractice.R

class CardStackAdapter(val context: Context, val items:List<String>): RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun binding(data:String){

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
    }

}