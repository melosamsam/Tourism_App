package com.example.tourism_app.data.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tourism_app.R

class ConditionsAdapter(
    private val conditionsList: ArrayList<String>
): RecyclerView.Adapter<ConditionsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.condition_card, parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return conditionsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val condition = conditionsList[position]

        holder.condition.text = condition
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val condition: TextView = itemView.findViewById(R.id.tvCondition)
    }
}