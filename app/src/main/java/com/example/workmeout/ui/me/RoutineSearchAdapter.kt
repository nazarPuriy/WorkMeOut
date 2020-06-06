package com.example.workmeout.ui.me

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.model.Routine
import com.example.workmeout.ui.sport.ExerciseDescriptionActivity

class RoutineSearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    lateinit var context: Context
    private var rList: List<Routine> = ArrayList()
    lateinit var vh: SportViewHolderRoutine

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.context).inflate(R.layout.sport_card_search, parent, false)
        vh = SportViewHolderRoutine(v)
        context = parent.context
        return vh
    }

    fun submitList( list: List<Routine>){
        this.rList = list
    }


    override fun getItemCount(): Int {
        return rList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is SportViewHolderRoutine ->{
                holder.bind(rList.get(position))
            }
        }
    }


    class SportViewHolderRoutine constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.name)
        val addButton = itemView.findViewById<ImageButton>(R.id.addicon)

        fun bind(routine:Routine) {

            name.text = routine.name
            addButton.setOnClickListener{
               Controlador.addExistingRoutine(routine, itemView.context)
            }

        }



    }


}

