package com.example.workmeout.ui.me

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.R
import com.example.workmeout.model.Routine
import kotlinx.android.synthetic.main.routine_card.view.*

class RoutineAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context
    private var items: List<Routine> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card, parent, false)
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
        val name = itemView.name
        val card:CardView = itemView.findViewById(R.id.card)
        val daysOfTheWeek = arrayListOf<String>("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        fun bind(routine: Routine) {

            name.text = routine.name

            var daysString = ""
            var tmp: Int = routine.days
            for (x in daysOfTheWeek) {
                if (tmp % 2 == 1) {
                    daysString += x
                    daysString += ", "
                }
                tmp /= 2
            }
            daysString = daysString.substringBeforeLast(",")

            days.text = daysString

            card.setOnClickListener(View.OnClickListener {
                val intent:Intent = Intent(itemView.context, RoutineActivity::class.java)
                intent.putExtra("isNew", false)
                intent.putExtra("position", adapterPosition)
                intent.putExtra("exists", true)
                itemView.context.startActivity(intent)
            })



        }

    }


}