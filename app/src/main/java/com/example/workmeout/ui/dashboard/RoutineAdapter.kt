package com.example.workmeout.ui.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.R
import kotlinx.android.synthetic.main.routine.view.*

class RoutineAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context
    private var items: List<Routine> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine, parent, false)
        val vh = RoutineViewHolder(v)
        context = parent.context
        return vh
    }

    fun submitList(blogList: List<Routine>){
        items = blogList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is RoutineViewHolder ->{
                holder.bind(items.get(position))
            }
        }


    }

    class RoutineViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val days = itemView.textView3
        val name = itemView.textView5

        fun bind(routine:Routine) {

            days.text = routine.days
            name.text = routine.name

        }

    }


}