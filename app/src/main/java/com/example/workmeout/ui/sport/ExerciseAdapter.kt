package com.example.workmeout.ui.sport

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador
import kotlinx.android.synthetic.main.sport_card.view.*



import com.example.workmeout.R
import com.example.workmeout.model.Exercise
import com.example.workmeout.model.Routine
import java.util.*


class ExerciseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var date: Date
    lateinit var context: Context
    private lateinit var routine: Routine
    private lateinit var sf:SportFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_card, parent, false)
        val npicker = v.findViewById<NumberPicker>(R.id.npicker_1)
        npicker.maxValue = 100
        npicker.minValue = 0
        val vh = SportViewHolder(v, sf)
        context = parent.context
        return vh
    }

    fun submitRoutine(routine:Routine){
        this.routine= routine
    }

    fun submitFragment(sf:SportFragment){
        this.sf = sf
    }

    override fun getItemCount(): Int {
        return routine.exercises_class.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is SportViewHolder ->{
                holder.bind(routine.exercises_class.get(position), date)
            }
        }


    }

    fun submitDate(date: Date) {
        this.date = date
    }

    class SportViewHolder constructor(itemView: View, sf:SportFragment) : RecyclerView.ViewHolder(itemView) {

        val currentWeight = itemView.npicker_1
        val name = itemView.name
        val cb: CheckBox = itemView.checkBox
        val reps: TextView = itemView.reps
        val card: CardView = itemView.cv
        val sf: SportFragment = sf


        fun bind(exercise: Exercise, date: Date) {
            currentWeight.value = exercise.weightAtDate(date)
            cb.isChecked = exercise.isDoneAtDate(date)
            name.text = exercise.name
            reps.text = "Reps: " + exercise.reps.toString()
            currentWeight.isEnabled = !exercise.isDoneAtDate(date)

            cb.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    exercise.addEvent(date, currentWeight.value)
                } else {
                    exercise.removeEvent(date)
                }
                Controlador.editUserExercise(exercise, itemView.context)
                sf.notifyBar()
                currentWeight.isEnabled = !cb.isChecked
            }

            card.setOnClickListener(View.OnClickListener {
                val intent: Intent = Intent(itemView.context, ExerciseActivity::class.java)
                intent.putExtra("exerciseId", exercise.id)
                itemView.context.startActivity(intent)
            })

            currentWeight.setOnValueChangedListener{ numberPicker: NumberPicker, i: Int, i1: Int ->

                if(cb.isChecked){
                    exercise.addEvent(date, currentWeight.value)
                    Controlador.editUserExercise(exercise, itemView.context)
                }

            }
        }

    }


}
