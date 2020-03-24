package com.example.workmeout.ui.sport

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.sport_card.view.*



import com.example.workmeout.R
import com.example.workmeout.model.Exercise


class ExerciseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context
    private var items: List<Exercise> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_card, parent, false)
        val npicker = v.findViewById<NumberPicker>(R.id.npicker)
        npicker.maxValue = 100
        npicker.minValue = 0
        val vh = SportViewHolder(v)
        context = parent.context
        return vh
    }

    fun submitList(blogList: List<Exercise>){
        items = blogList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is SportViewHolder ->{
                holder.bind(items.get(position))
            }
        }


    }

    class SportViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val currentWeight = itemView.npicker
        val name = itemView.textView5
        val cb: CheckBox = itemView.checkBox


        fun bind(exercise: Exercise) {
            currentWeight.value = exercise.currentWeight
            name.text = exercise.name
            cb.setOnCheckedChangeListener { buttonView, isChecked ->
                currentWeight.isEnabled = !isChecked
            }
            name.setOnClickListener(View.OnClickListener {
                val intent: Intent = Intent(itemView.context, ExerciseActivity::class.java)
                intent.putExtra("exName",name.text.toString())
                intent.putExtra("exWeight",currentWeight.value.div(1.0))
                itemView.context.startActivity(intent)
            })

        }

    }


}
