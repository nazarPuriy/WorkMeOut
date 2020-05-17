package com.example.workmeout.ui.me

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.model.RoutineOLD
import com.example.workmeout.R
import com.example.workmeout.data.ExerciseDataSourceDummy
import com.example.workmeout.model.Exercise
import com.example.workmeout.model.Routine
import com.example.workmeout.ui.sport.ExerciseDescriptionActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_routine.*
import org.jetbrains.anko.find

class RoutineActivity : AppCompatActivity() {
    private var isOpen = false
    lateinit var editTitle : EditText
    lateinit var editDescription : EditText
    lateinit var textTitle : TextView
    lateinit var textDescription : TextView
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
    lateinit var rv: RecyclerView
    lateinit var sa:ExerciseRoutineAdapter

    var indexRoutine:Int = -1
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

        for(x in days){

            x.setOnCheckedChangeListener { buttonView, isChecked ->
                update()
            }
        }

        editTitle = findViewById(R.id.title)
        editDescription = findViewById(R.id.edt_description)
        isNew = intent.getBooleanExtra("isNew", false)

        rv = findViewById(R.id.rv_1)
        sa = ExerciseRoutineAdapter()

        sa.submitRoutine(Routine(0, 0, "", "", 0))
        rv.adapter = sa
        rv.layoutManager = LinearLayoutManager(this)

        if(!isNew){
            exists = true
            editDescription.isEnabled = false
            editTitle.isEnabled = false
            indexRoutine = intent.getIntExtra("position", -1)

            btnsave.isEnabled = false
            refreshRoutine()
        }else{
            editTitle.hint = "Routine title"
            editDescription.hint = "Routine description"
        }




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
        val routine:Routine = Controlador.getRoutines().get(indexRoutine)
        editTitle.setText(routine.name)
        editDescription.setText(routine.description)
        decodeDays(routine.days)
        sa.submitRoutine(routine)
        sa.notifyDataSetChanged()

    }

    override fun onStart(){
        super.onStart()
        init()
    }

    fun init(){
        val saveBtn : Button = findViewById(R.id.btnsave)
        val delateBtn : Button = findViewById(R.id.dlt)
        editDescription = findViewById(R.id.edt_description)
        editTitle = findViewById(R.id.title)
        textTitle = findViewById(R.id.txt_tittle)
        textDescription = findViewById(R.id.txt_description)

        if(isOpen){
            clickAdd()
        }

        if(!exists){
            saveBtn.visibility = View.VISIBLE
            delateBtn.visibility = View.INVISIBLE
            editDescription.visibility =  View.VISIBLE
            editTitle.visibility = View.VISIBLE
            textDescription.visibility = View.INVISIBLE
            textTitle.visibility = View.INVISIBLE

            for(x in days){
                x.isEnabled = false
            }

        }else{
            saveBtn.visibility = View.INVISIBLE
            delateBtn.visibility = View.VISIBLE
            editDescription.visibility =  View.INVISIBLE
            editTitle.visibility = View.INVISIBLE
            textDescription.setText(editDescription.text.toString())
            textTitle.setText(editTitle.text.toString())
            textDescription.visibility = View.VISIBLE
            textTitle.visibility = View.VISIBLE

            for(x in days){
                x.isEnabled = true
            }

            refreshRoutine()
        }
    }

    fun delete(v: View) {
        Controlador.deleteRoutine(v.context,indexRoutine)
        finish()
    }

    fun addExerciseNew(){
        val descriptionIntent = Intent(this, ExerciseDescriptionActivity::class.java)
        descriptionIntent.putExtra("MODE","1")
        descriptionIntent.putExtra("title", "Exercise Title")
        descriptionIntent.putExtra("isNew", true)
        descriptionIntent.putExtra("index", indexRoutine + 1)
        startActivity(descriptionIntent)
    }

    fun searchExercise(){
        val searchIntent = Intent(this, SearchExercises::class.java)
        searchIntent.putExtra("routine", indexRoutine + 1)
        startActivity(searchIntent)
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
            else if(Controlador.getRoutines().get(indexRoutine).numberOfExercises >= 15){
                Toast.makeText(this, "Routines can have a maximum of 15 exercises", Toast.LENGTH_SHORT).show()
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

    private fun decodeDays(n: Int) {

        var tmp: Int = n

        for (x in days){

            x.isChecked = (tmp % 2 == 1)
            tmp /= 2

        }


    }

    fun update(){
        Controlador.editRoutineDays(indexRoutine, calcDays(), this)
    }

    fun save(){

        if(isNew){

            if(editTitle.text.isEmpty() || editDescription.text.isEmpty()){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT)
            }

            Controlador.registerRoutine(this, editTitle.text.toString(), editDescription.text.toString(),
                0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, calcDays())
            btnsave.isEnabled = false
            indexRoutine = Controlador.currentUser!!.numberOfRoutines -1
        }else{//TODO editar rutina de usuario

        }
        exists = true
        init()
    }
}
