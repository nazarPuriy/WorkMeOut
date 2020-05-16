package com.example.workmeout.ui.me

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador


import com.example.workmeout.R
import com.example.workmeout.model.Exercise
import com.example.workmeout.ui.sport.ExerciseDescriptionActivity


class ExerciseSearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context
    private var exList: List<Exercise> = ArrayList()
    lateinit var vh: SportViewHolderRoutine
    var routineIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.context).inflate(R.layout.sport_card_search, parent, false)
        vh = SportViewHolderRoutine(v)
        vh.indexRoutine = routineIndex
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
        val addButton = itemView.findViewById<ImageButton>(R.id.addicon)
        var indexRoutine:Int= 0

        fun bind(exercise: Exercise) {

            name.text = exercise.name

            card.setOnClickListener{
                val descriptionIntent:Intent = Intent(itemView.context, ExerciseDescriptionActivity::class.java)
                descriptionIntent.putExtra("MODE","0")
                descriptionIntent.putExtra("title", exercise.name)
                descriptionIntent.putExtra("description", exercise.description)
                itemView.context.startActivity(descriptionIntent)
            }

            addButton.setOnClickListener{
                Controlador.guardarEjercioRutina(exercise, indexRoutine, itemView.context)
            }
        }



    }


}
