package com.example.workmeout.ui.sport

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.model.Routine
import com.example.workmeout.ui.me.RoutineActivity
import kotlinx.android.synthetic.main.routine_element.view.*
import java.util.*

class RoutineListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context
    private lateinit var sf:SportFragment
    private lateinit var date: Date

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_element, parent, false)
        val vh = RoutineItemViewHolder(v, sf)
        context = parent.context
        return vh
    }

    fun submitFragment(sf:SportFragment){
        this.sf = sf
    }

    override fun getItemCount(): Int {
        return Controlador.getRoutinesOnDate(date).size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is RoutineItemViewHolder ->{
                holder.bind(Controlador.getRoutinesOnDate(date).get(position), date)
            }
        }


    }

    fun submitDate(date: Date) {
        this.date =date
    }

    class RoutineItemViewHolder constructor(itemView: View, sf:SportFragment) : RecyclerView.ViewHolder(itemView) {

        val sf: SportFragment = sf
        val routineRecycler = itemView.rv_routine
        val titleTv = itemView.tv_routineTitle

        fun bind(routine: Routine, date: Date) {

            routineRecycler.layoutManager = LinearLayoutManager(itemView.context)
            var adapter: ExerciseAdapter = ExerciseAdapter()
            routineRecycler.adapter = adapter
            adapter.submitRoutine(routine)
            adapter.submitFragment(sf)
            adapter.submitDate(date)
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