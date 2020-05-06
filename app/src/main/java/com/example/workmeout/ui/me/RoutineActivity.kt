package com.example.workmeout.ui.me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.model.RoutineOLD
import com.example.workmeout.R
import com.example.workmeout.data.ExerciseDataSourceDummy
import com.example.workmeout.ui.sport.ExerciseDescriptionActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_routine.*
import org.jetbrains.anko.find

class RoutineActivity : AppCompatActivity() {
    private var isOpen = false
    lateinit var editTitle : EditText
    lateinit var editDescription : EditText


    lateinit var add_button:FloatingActionButton
    lateinit var search_button:FloatingActionButton
    lateinit var add_people_button:FloatingActionButton
    lateinit var save_button:Button

    lateinit var mon:CheckBox
    lateinit var tue:CheckBox
    lateinit var wed:CheckBox
    lateinit var thu:CheckBox
    lateinit var fri:CheckBox
    lateinit var sat:CheckBox
    lateinit var sun:CheckBox
    var days:ArrayList<CheckBox> = ArrayList()
    var exercises:ArrayList<Int> = ArrayList()
    var idRoutine:Int = 0


    var exists:Boolean = false
    var isNew:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)

        add_button = findViewById<FloatingActionButton>(R.id.fab)
        search_button = findViewById<FloatingActionButton>(R.id.search)
        add_people_button = findViewById<FloatingActionButton>(R.id.new_button)
        save_button = findViewById<Button>(R.id.btnsave)


        mon = findViewById(R.id.MON)
        days.add(mon)

        tue = findViewById(R.id.TUE)
        days.add(tue)

        wed = findViewById(R.id.WED)
        days.add(wed)

        thu = findViewById(R.id.THU)
        days.add(thu)

        fri = findViewById(R.id.FRI)
        days.add(fri)

        sat = findViewById(R.id.SAT)
        days.add(sat)

        sun = findViewById(R.id.SUN)
        days.add(sun)

        editTitle = findViewById(R.id.title)
        editDescription = findViewById(R.id.edt_description)
        isNew = intent.getBooleanExtra("isNew", false)

        if(!isNew){
            exists = true//TODO
            editDescription.isEnabled = false
            editTitle.isEnabled = false
            idRoutine = intent.getIntExtra("id", 0)
            btnsave.isEnabled = false
            refreshRoutine()
        }else{
            editTitle.hint = "Routine title"
            editDescription.hint = "Routine description"
        }

        var rv = findViewById<RecyclerView>(R.id.rv_1)
        val sa = ExerciseRoutineAdapter()
        sa.submitRoutine(RoutineOLD("", ""))
        rv.adapter = sa
        rv.layoutManager = LinearLayoutManager(this)



        add_button.setOnClickListener {
            clickAdd()
        }

        search_button.setOnClickListener {
            searchExercise()
        }

        add_people_button.setOnClickListener{
            addExerciseNew()
        }

        save_button.setOnClickListener{
            save()
        }





        var routineOLD: RoutineOLD = ExerciseDataSourceDummy.createDataSet()

    }

    private fun refreshRoutine() {
        //TODO actualizar los ejercicios
        //TODO actualizar los checkboxes
        //TODO actualizar título y descripcion
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
        searchIntent.putExtra("routine", idRoutine)
        startActivity(searchIntent)
    }

    //TODO days buen formato
    fun addRoutine(view:View){
        Controlador.registerRoutine(view.context,editTitle.text.toString(),editDescription.text.toString(),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,100)
    }

    fun clickAdd(){

        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        val fabRClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        val fabRAntiClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)

        if(exists){
            if(!isNew){Toast.makeText(this, "You can't edit this routine", Toast.LENGTH_SHORT).show()}
            else if (isOpen) {

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
        }else if(isNew){
            Toast.makeText(this, "Please save the routine before adding exercises", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Database error, try again", Toast.LENGTH_SHORT)
            finish()
        }


    }

    fun calcDays():Int{
        var sum:Int = 0
        var power:Int = 1
        for(x in 0 until days.size){
            if(days.get(x).isChecked){
                sum += power
            }
            power*=2
        }

        return sum
    }

    fun save(){

        if(isNew){

            if(editTitle.text.isEmpty() || editDescription.text.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
            }

            Controlador.registerRoutine(this, "title", "description",
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, calcDays())
            btnsave.isEnabled = false
        }else{//TODO editar rutina de usuario
        }
        exists = true
    }
}
