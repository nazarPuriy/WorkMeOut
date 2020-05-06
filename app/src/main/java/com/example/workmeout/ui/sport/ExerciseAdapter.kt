package com.example.workmeout.ui.sport

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.model.ExerciseOLD
import com.example.workmeout.model.RoutineOLD
import kotlinx.android.synthetic.main.sport_card.view.*



import com.example.workmeout.R



class ExerciseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context
    private lateinit var routineOLD: RoutineOLD
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

    fun submitRoutine(routineOLD: RoutineOLD){
        this.routineOLD= routineOLD
    }

    fun submitFragment(sf:SportFragment){
        this.sf = sf
    }

    override fun getItemCount(): Int {
        return routineOLD.exerciseOLDList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is SportViewHolder ->{
                holder.bind(routineOLD.exerciseOLDList.get(position))
            }
        }


    }

    class SportViewHolder constructor(itemView: View, sf:SportFragment) : RecyclerView.ViewHolder(itemView) {

        val currentWeight = itemView.npicker_1
        val name = itemView.name
        val cb: CheckBox = itemView.checkBox
        val reps: TextView = itemView.reps
        val card: CardView = itemView.cv
        val sf: SportFragment = sf


        fun bind(exerciseOLD: ExerciseOLD) {
            currentWeight.value = exerciseOLD.currentWeight
            name.text = exerciseOLD.name
            reps.text = "Reps: " + exerciseOLD.reps.toString()
            cb.isChecked = exerciseOLD.done
            currentWeight.isEnabled = !exerciseOLD.done
            cb.setOnCheckedChangeListener { buttonView, isChecked ->
                currentWeight.isEnabled = !isChecked
                exerciseOLD.done = isChecked
                exerciseOLD.currentWeight = currentWeight.value
                sf.notifyBar()
            }

            card.setOnClickListener(View.OnClickListener {
                val intent: Intent = Intent(itemView.context, ExerciseActivity::class.java)
                intent.putExtra("exName",name.text.toString())
                intent.putExtra("exWeight",currentWeight.value.div(1.0))
                intent.putExtra("exReps", exerciseOLD.reps)
                itemView.context.startActivity(intent)
            })
        }

    }


}
