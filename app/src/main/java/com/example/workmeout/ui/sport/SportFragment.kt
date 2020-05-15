package com.example.workmeout.ui.sport

import android.animation.ObjectAnimator
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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.model.Routine
import java.text.SimpleDateFormat
import java.util.*

class SportFragment : Fragment() {

    lateinit var pb:ProgressBar
    lateinit var reps: TextView
    lateinit var rv: RecyclerView
    lateinit var next: ImageButton
    lateinit var previous: ImageButton


    val progressMultiplier: Int = 1000
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
        refresh()
    }

    fun notifyBar(){
        var total = getTotalReps()
        pb.max = total*progressMultiplier

        var done = getDoneReps()
        val animator: ObjectAnimator = ObjectAnimator.ofInt(pb, "progress", pb.progress, done*progressMultiplier)
        animator.setDuration(200)
        val ip: Interpolator = AccelerateDecelerateInterpolator()
        animator.interpolator = ip
        animator.start()

        reps.text = "Today's reps: " + done.toString() + "/" + total.toString()
    }

    private fun getDoneReps(): Int {
        return 3//TODO
    }

    private fun getTotalReps(): Int {
        return 10//TODO
    }

    private fun next(){

    }

    private fun previous(){

    }


    fun refresh(){

        var routines:List<Routine> = Controlador.getRoutinesOnDate(date)
        sa.submitRoutines(routines)
        sa.notifyDataSetChanged()

        val formatter = SimpleDateFormat()

        notifyBar()

    }




}
