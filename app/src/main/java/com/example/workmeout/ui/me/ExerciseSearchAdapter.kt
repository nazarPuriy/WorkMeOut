package com.example.workmeout.ui.me

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


import com.example.workmeout.R
import com.example.workmeout.model.Exercise


class ExerciseSearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context
    private var exList: List<Exercise> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_card_search, parent, false)
        val vh = SportViewHolderRoutine(v)
        context = parent.context
        return vh
    }

    fun submitList( exList: List<Exercise>){
        this.exList = exList
    }


    override fun getItemCount(): Int {
        return exList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is SportViewHolderRoutine ->{
                holder.bind(exList.get(position))
            }
        }
    }

    class SportViewHolderRoutine constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.name)
        val card = itemView.findViewById<CardView>(R.id.cv)

        fun bind(exercise: Exercise) {
            name.text = exercise.name

        }

    }


}
