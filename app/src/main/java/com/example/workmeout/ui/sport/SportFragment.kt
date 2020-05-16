package com.example.workmeout.ui.sport

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.model.Routine
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

class SportFragment : Fragment() {

    lateinit var pb:ProgressBar
    lateinit var reps: TextView
    lateinit var rv: RecyclerView
    lateinit var next: ImageButton
    lateinit var previous: ImageButton
    lateinit var routinesText: TextView


    val progressMultiplier: Int = 10000
    var date: Date = Date()
    val sa: RoutineListAdapter = RoutineListAdapter()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)


        pb = root.findViewById<ProgressBar>(R.id.progressBar1)
        reps = root.findViewById(R.id.reps)
        next = root.findViewById(R.id.right_arrow)
        previous = root.findViewById(R.id.left_arrow)
        routinesText = root.findViewById(R.id.routine)


        rv = root.findViewById<RecyclerView>(R.id.rv_1)
        sa.submitFragment(this)
        rv.layoutManager = LinearLayoutManager(root.context)
        rv.adapter = sa


        next.setOnClickListener{next()}
        previous.setOnClickListener{previous()}


        return root
    }

    override fun onStart() {
        super.onStart()
        date = Date()
        refresh()
    }

    fun notifyBar(){
        var total = getTotalReps()

        var previousMax = pb.max
        var newMax = total*progressMultiplier
        pb.max = newMax
        var animateFrom = 0

        if(previousMax == newMax){
            animateFrom = pb.progress
        }


        var done = getDoneReps()
        val animator: ObjectAnimator = ObjectAnimator.ofInt(pb, "progress", animateFrom, done*progressMultiplier)
        animator.setDuration(250)
        val ip: Interpolator = AccelerateDecelerateInterpolator()
        animator.interpolator = ip
        animator.start()

        reps.text = "Today's reps: " + done.toString() + "/" + total.toString()
    }

    private fun getDoneReps(): Int {

        var total = 0

        for(routine in Controlador.getRoutinesOnDate(date)){
            for (exercise in routine.exercises_class){
                if(exercise.isDoneAtDate(date)){
                    total += exercise.reps
                }
            }
        }

        return total
    }

    private fun getTotalReps(): Int {

        var total = 0

        for(routine in Controlador.getRoutinesOnDate(date)){
            for (exercise in routine.exercises_class){
                total += exercise.reps
            }
        }

        return total
    }


    private fun next(){
        val cal:Calendar = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DATE, 1)
        date = cal.time
        refresh()
    }

    private fun previous(){
        val cal:Calendar = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DATE, -1)
        date = cal.time
        refresh()
    }


    fun refresh(){

        sa.submitDate(date)
        sa.notifyDataSetChanged()

        val formatter = SimpleDateFormat("dd/MM/yyyy")
        routinesText.setText("Routines on " + formatter.format(date))
        notifyBar()

    }




}
