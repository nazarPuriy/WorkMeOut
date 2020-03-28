package com.example.workmeout.ui.me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.R
import com.example.workmeout.data.ExerciseDataSourceDummy
import com.example.workmeout.ui.sport.ExerciseDescriptionActivity

class RoutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)
        var rv = findViewById<RecyclerView>(R.id.rv_1)
        val sa = ExerciseRoutineAdapter()
        sa.submitRoutine(ExerciseDataSourceDummy.createDataSet())
        rv.adapter = sa
        rv.layoutManager = LinearLayoutManager(this)
        var button1:Button = findViewById(R.id.dlt)

    }

    fun delete(v: View) {
        Toast.makeText(this, "Delete this routine", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun addExercise(view:View){
        val descriptionIntent = Intent(this, ExerciseDescriptionActivity::class.java)
        descriptionIntent.putExtra("MODE","1")
        descriptionIntent.putExtra("title", "Exercise Title")
        startActivity(descriptionIntent)
    }
}
