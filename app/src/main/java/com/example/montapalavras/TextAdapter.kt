package com.example.montapalavras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TextAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: List<Char> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val  text = LayoutInflater.from(parent.context).inflate(R.layout.textcontent, parent,false)

        return textViewHolder(text)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = items[position]
        if(holder is textViewHolder){
            holder.cTextView.text = currentItem.toString()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class textViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cTextView: TextView = itemView.findViewById(R.id.textcontent)
    }
}