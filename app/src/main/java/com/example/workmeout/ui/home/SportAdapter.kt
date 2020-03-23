package com.example.workmeout.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.example.workmeout.R


class SportAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_card, parent, false)
        val npicker = v.findViewById<NumberPicker>(R.id.npicker)
        npicker.maxValue = 100
        npicker.minValue = 0
        val vh = SportViewHolder(v)
        context = parent.context
        return vh
    }

    override fun getItemCount(): Int {
        return 4 //TODO
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


    }

    internal class SportViewHolder(itemView: View) :
        ViewHolder(itemView) {
    }


}
