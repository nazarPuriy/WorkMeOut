package com.example.workmeout.ui.me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.R
import com.example.workmeout.data.ExerciseDataSourceDummy
import com.example.workmeout.model.Routine
import com.example.workmeout.ui.sport.ExerciseDescriptionActivity

class RoutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)

        val isNew: Boolean = intent.getBooleanExtra("isNew", false)
        val name: String = intent.getStringExtra("name")

        var rv = findViewById<RecyclerView>(R.id.rv_1)
        val sa = ExerciseRoutineAdapter()
        var routine: Routine = ExerciseDataSourceDummy.createDataSet()
        if(isNew){routine = Routine("New Routine", "")}
        sa.submitRoutine(routine)
        rv.adapter = sa
        rv.layoutManager = LinearLayoutManager(this)

        val title: EditText = findViewById(R.id.title)
        title.setText(name)
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
