package com.example.workmeout.ui.me

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sport_card.view.*



import com.example.workmeout.R
import com.example.workmeout.model.Exercise
import com.example.workmeout.model.Routine
import com.example.workmeout.ui.sport.ExerciseActivity


class ExerciseRoutineAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context
    private lateinit var routine: Routine

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_card_routine, parent, false)
        val vh = SportViewHolderRoutine(v)
        context = parent.context
        return vh
    }

    fun submitRoutine(routine: Routine){
        this.routine= routine
    }


    override fun getItemCount(): Int {
        return routine.exercises_class.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is SportViewHolderRoutine ->{
                holder.bind(routine.exercises_class.get(position), routine, this)
            }
        }
    }

    class SportViewHolderRoutine constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name = itemView.name
        val reps: TextView = itemView.reps
        val card: CardView = itemView.cv

        fun bind(exercise: Exercise, routine: Routine, era:ExerciseRoutineAdapter) {
            name.text = exercise.name
            reps.text = "Reps: " + exercise.reps.toString()

            card.setOnClickListener(View.OnClickListener {
                val intent = Intent(itemView.context, ExerciseActivity::class.java)
                intent.putExtra("exerciseId", exercise.id)
                itemView.context.startActivity(intent)
            })

        }

    }


}
