package com.example.workmeout.model

import android.content.Context
import android.widget.Toast
import com.example.workmeout.Controlador.Controlador
import kotlinx.coroutines.selects.select
import java.lang.Exception
import java.nio.channels.Selector

class Routine {


    fun notifyExerciseReady(context: Context) {

        if (exerciseReadyCount == idCount()){

            exercises_class.sortBy {
                it.id
            }

            Controlador.notifyRoutineReady(context)
        }

        exerciseReadyCount++


    }

    fun idCount():Int{

        var count:Int = 0

        for(x in exercises){
            if(x != 0){
                count++
            }
        }

        return count
    }

    //Todos los atributos del usuario
    var id : Int //Utilizaremos este como identificador único de la rutina del usuario.
    var exerciseReadyCount = 0
    var classid : Int
    var name : String
    var description : String
    var days : Int
    var exercises: MutableList<Int>
    var exercises_class: ArrayList<Exercise>
    var exercisesDesc: MutableList<Int>
    var numberOfExercises : Int

    //Constructor principal de la clase usuario
    constructor(id : Int, classid: Int, name: String, description : String, days: Int){
        this.id = id
        this.classid = classid
        this.name = name
        this.description = description
        this.days = days
        numberOfExercises = 0
        exercises = mutableListOf() //Creamos la lista demomento sin ejercicios
        exercises_class = ArrayList()
        exercisesDesc = mutableListOf()
    }


}