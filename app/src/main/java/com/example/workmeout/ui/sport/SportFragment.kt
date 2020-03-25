package com.example.workmeout.ui.sport

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.R
import com.example.workmeout.data.ExerciseDataSourceDummy
import com.example.workmeout.ui.me.RoutineActivity

class SportFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        var pb = root.findViewById<ProgressBar>(R.id.progressBar1)
        pb.max = 30
        pb.progress = 6

        var rv = root.findViewById<RecyclerView>(R.id.rv_1)
        val sa = ExerciseAdapter()
        sa.submitList(ExerciseDataSourceDummy.createDataSet())
        rv.adapter = sa
        rv.layoutManager = LinearLayoutManager(root.context)

        var routine: TextView = root.findViewById<TextView>(R.id.routine)
        routine.setOnClickListener(View.OnClickListener {
            var intent:Intent = Intent(context, RoutineActivity::class.java)
            startActivity(intent)
        })



        return root
    }
}
