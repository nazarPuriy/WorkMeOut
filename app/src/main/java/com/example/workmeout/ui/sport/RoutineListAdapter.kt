package com.example.workmeout.ui.sport

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.model.Routine
import com.example.workmeout.ui.me.RoutineActivity
import kotlinx.android.synthetic.main.routine_element.view.*
import kotlinx.android.synthetic.main.sport_card.view.*

class RoutineListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context
    private lateinit var routines:List<Routine>
    private lateinit var sf:SportFragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_element, parent, false)
        val vh = RoutineItemViewHolder(v, sf)
        context = parent.context
        return vh
    }

    fun submitRoutines(routines: List<Routine>){
        this.routines= routines
    }

    fun submitFragment(sf:SportFragment){
        this.sf = sf
    }

    override fun getItemCount(): Int {
        return routines.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is RoutineItemViewHolder ->{
                holder.bind(routines.get(position))
            }
        }


    }

    class RoutineItemViewHolder constructor(itemView: View, sf:SportFragment) : RecyclerView.ViewHolder(itemView) {

        val sf: SportFragment = sf
        val routineRecycler = itemView.rv_routine
        val titleTv = itemView.tv_routineTitle
        fun bind(routine: Routine) {

            routineRecycler.layoutManager = LinearLayoutManager(itemView.context)
            var adapter: ExerciseAdapter = ExerciseAdapter()
            routineRecycler.adapter = adapter
            adapter.submitRoutine(routine)
            adapter.submitFragment(sf)
            adapter.notifyDataSetChanged()

            titleTv.setText(routine.name)
            titleTv.setOnClickListener{

                val intent = Intent(itemView.context, RoutineActivity::class.java)
                intent.putExtra("isNew", false)
                intent.putExtra("exists", true)
                intent.putExtra("position", Controlador.getRoutineIndexById(routine.id))
                itemView.context.startActivity(intent)

            }
        }

    }
}