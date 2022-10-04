package com.monitoring.cellshark.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.monitoring.cellshark.R


internal class MyRecyclerViewAdapter(private var itemsList: List<String>): RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

        internal inner class MyViewHolder(view: View) : ViewHolder(view) {
            var itemTextView: TextView = view.findViewById(R.id.itemTextView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item = itemsList[position]
            holder.itemTextView.text = item
        }

        override fun getItemCount(): Int {
            return itemsList.size
        }
}
