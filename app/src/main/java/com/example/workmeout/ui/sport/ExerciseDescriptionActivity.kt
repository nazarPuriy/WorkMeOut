package com.example.workmeout.ui.sport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R

class ExerciseDescriptionActivity : AppCompatActivity() {//TODO repeticiones
    private val EDITAR = 1
    private var modoHint = false
    private var modo = 0
    private lateinit var title_edit : EditText
    private lateinit var description_edit : EditText
    private lateinit var title_text : TextView
    private lateinit var description_text : TextView
    private lateinit var button : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_description)
        modo = Integer.parseInt(getIntent().getExtras()!!.getString("MODE","0"))
        if(modo==EDITAR){
            modoHint=true
        }

        title_text=findViewById(R.id.txt_tittle_EV)
        description_text=findViewById(R.id.txt_description_EV)

        title_edit=findViewById(R.id.edttxt_tittle_EV)
        title_edit.setText(intent.getStringExtra("title"))

        description_edit=findViewById(R.id.edttxt_description_EV)
        description_edit.setText( intent.getStringExtra("description"))

        button=findViewById(R.id.btn_save_EV)
        if(modo==EDITAR){
            editMode()
        }else{
            descriptionMode()
        }
    }

    /***
     * Función que carga toda la parte visual de la activity en forma de texto visual
     */
    private fun descriptionMode(){
        title_text.text = title_edit.text.toString()
        description_text.text = description_edit.text.toString()
        title_text.visibility=View.VISIBLE
        description_text.visibility=View.VISIBLE
        title_edit.visibility=View.INVISIBLE
        description_edit.visibility=View.INVISIBLE

        button.visibility = View.GONE
    }

    /***
     * Función que carga toda la parte visual de la activity en forma de editar
     */
    fun editMode(){
        if(modoHint){
            title_edit.hint=title_text.text.toString()
            description_edit.hint="Exercice description"
            title_edit.setText("")
            description_edit.setText("")
            modoHint=false
        }else{
            title_edit.setText(title_text.text.toString())
            description_edit.setText(description_text.text.toString())
        }

        title_text.visibility=View.INVISIBLE
        description_text.visibility=View.INVISIBLE
        title_edit.visibility=View.VISIBLE
        description_edit.visibility=View.VISIBLE
        button.text = "Save"
    }

    /***
     * Función que se llama al crear un nuevo ejercicio. Deberia de añadirlo a la base de datos.
     */
    fun save(view:View){
        if(modo==EDITAR){
            if(checkInfo()){
                val routineIndex:Int = intent.getIntExtra("index", 0)
                Controlador.registerExercise(this, title_edit.text.toString(), description_edit.text.toString(),0,0, routineIndex)//TODO cambiar valores para que se muestren los del activity


                modo=0
                descriptionMode()
            }else{
                Toast.makeText(this,"Some fields are still empty",Toast.LENGTH_SHORT).show()
            }
        }else{
            modo=1
            editMode()
        }
    }

    private fun checkInfo() : Boolean{
        if(title_edit.text.isNullOrEmpty()){
            return false
        }
        if(description_edit.text.isNullOrEmpty()){
            return false
        }
        return true
    }
}
