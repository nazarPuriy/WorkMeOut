package com.example.workmeout.ui.me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.model.Routine
import com.example.workmeout.R
import com.example.workmeout.data.ExerciseDataSourceDummy
import com.example.workmeout.ui.sport.ExerciseDescriptionActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.jetbrains.anko.find

class RoutineActivity : AppCompatActivity() {
    private var isOpen = false
    lateinit var editTitle : EditText
    lateinit var editDescription : EditText
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)

        editTitle = findViewById(R.id.title)
        editDescription = findViewById(R.id.edt_description)
        val isNew: Boolean = intent.getBooleanExtra("isNew", false)
        val name: String = intent.getStringExtra("name")

        var rv = findViewById<RecyclerView>(R.id.rv_1)
        val sa = ExerciseRoutineAdapter()
        var routine: Routine = ExerciseDataSourceDummy.createDataSet()
        if(isNew){routine =
            Routine("New Routine", "")
        }
        sa.submitRoutine(routine)
        rv.adapter = sa
        rv.layoutManager = LinearLayoutManager(this)

        val title: EditText = findViewById(R.id.title)
        title.setText(name)



        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        val fabRClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        val fabRAntiClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)

        val add_button = findViewById<FloatingActionButton>(R.id.fab)
        val search_button = findViewById<FloatingActionButton>(R.id.search)
        val add_people_button = findViewById<FloatingActionButton>(R.id.new_button)



        add_button.setOnClickListener {

            if (isOpen) {

                search_button.startAnimation(fabClose)
                add_people_button.startAnimation(fabClose)
                add_button.startAnimation(fabRClockwise)

                isOpen = false
            }

            else {

                search_button.startAnimation(fabOpen)
                add_people_button.startAnimation(fabOpen)
                add_button.startAnimation(fabRAntiClockwise)

                search_button.isClickable
                add_people_button.isClickable

                isOpen = true
            }

            add_people_button.setOnClickListener{
                addExerciseNew()
            }


        }

        search_button.setOnClickListener {
            searchExercise()
        }

        add_people_button.setOnClickListener{
        }
    }

    fun delete(v: View) {
        Toast.makeText(this, "Delete this routine", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun addExerciseNew(){
        val descriptionIntent = Intent(this, ExerciseDescriptionActivity::class.java)
        descriptionIntent.putExtra("MODE","1")
        descriptionIntent.putExtra("title", "Exercise Title")
        startActivity(descriptionIntent)
    }

    fun searchExercise(){
        val searchIntent = Intent(this, SearchExercises::class.java)
        startActivity(searchIntent)
    }

    //TODO days buen formato
    fun addRoutine(view:View){
        Controlador.registerRoutine(view.context,editTitle.text.toString(),editDescription.text.toString(),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100)
    }
}
