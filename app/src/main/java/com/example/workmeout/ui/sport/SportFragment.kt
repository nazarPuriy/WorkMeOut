package com.example.workmeout.ui.sport

import android.animation.ObjectAnimator
import android.content.Intent
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
import com.example.workmeout.model.Routine
import com.example.workmeout.R
import com.example.workmeout.data.ExerciseDataSourceDummy
import com.example.workmeout.ui.me.RoutineActivity

class SportFragment : Fragment() {

    lateinit var pb:ProgressBar
    lateinit var reps: TextView
    lateinit var routine: Routine
    val progressMultiplier: Int = 1000


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        routine = ExerciseDataSourceDummy.createDataSet()
        pb = root.findViewById<ProgressBar>(R.id.progressBar1)
        reps = root.findViewById(R.id.reps)
        notifyBar()

        var rv = root.findViewById<RecyclerView>(R.id.rv_1)
        val sa = ExerciseAdapter()
        sa.submitFragment(this)
        sa.submitRoutine(routine)
        rv.adapter = sa
        rv.layoutManager = LinearLayoutManager(root.context)

        var routine: TextView = root.findViewById<TextView>(R.id.routine)
        routine.setOnClickListener(View.OnClickListener {
            var s : String = Controlador.currentUser!!.email;
            Toast.makeText(context,s,Toast.LENGTH_LONG).show() //Prubea de que el controlador es del tipo singleton.
            var intent:Intent = Intent(context, RoutineActivity::class.java)
            intent.putExtra("name", "Routine 1")
            startActivity(intent)
        })

        var next: ImageButton = root.findViewById(R.id.right_arrow)
        var previous: ImageButton = root.findViewById(R.id.left_arrow)

        next.setOnClickListener(View.OnClickListener { Toast.makeText(context, "Next day", Toast.LENGTH_SHORT).show() })
        previous.setOnClickListener(View.OnClickListener { Toast.makeText(context, "Previous day", Toast.LENGTH_SHORT).show() })



            return root
    }

    fun notifyBar(){
        var total = routine.getTotalReps()
        pb.max = total*progressMultiplier

        var done = routine.getDoneReps()
        val animator: ObjectAnimator = ObjectAnimator.ofInt(pb, "progress", pb.progress, done*progressMultiplier)
        animator.setDuration(200)
        val ip: Interpolator = AccelerateDecelerateInterpolator()
        animator.interpolator = ip
        animator.start()

        reps.text = "Today's reps: " + done.toString() + "/" + total.toString()
    }


}
