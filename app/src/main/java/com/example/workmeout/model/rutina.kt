package com.example.workmeout.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class rutina {
    var id: Int
    var name: String
    var fecha: Date
    var userFather: Usuari
    var exerciseList: ArrayList<ejercicio> = ArrayList()
    var daysToDo: HashMap<String,Boolean> = mapOf(Pair("Monday",false),
        Pair("Tuesday",false),Pair("Wednesday",false),Pair("Thursday",false),Pair("Friday",false),Pair("Saturday",false),Pair("Sunday",false)) as HashMap<String, Boolean>

    constructor(id: Int, name: String, fecha: Date, userFather: Usuari, exerciseList: ArrayList<ejercicio>){
        this.id = id
        this.name = name
        this.fecha = fecha
        this.userFather = userFather
        this.exerciseList = exerciseList;
    }

    fun addExercise(ex: ejercicio){
        if(!isExercise(ex.name)){
            exerciseList.add(ex)
        }else{
            //NOTIFICACION QUE NO SE PUEDE AÑADIR PORQUE YA ESTA
        }
    }

    fun deleteExercise(exName: String){
        var positionEx = exPosition(exName);
        if(positionEx == -1){
            //Mensaje de que no existe el ejercicio i no se puede eliminar
        }else{
            exerciseList.removeAt(positionEx)
        }
    }

    fun isExercise(exName: String): Boolean {
        val exerciseListIterator = exerciseList.iterator()
        while(exerciseListIterator.hasNext()){
            if(exerciseListIterator.next().name == exName)
                return true
        }
        return false
    }

    fun exPosition(exName: String): Int{
        val exerciseListIterator = exerciseList.iterator()
        while(exerciseListIterator.hasNext()){
            var mom : ejercicio = exerciseListIterator.next()
            if(mom.name == exName){
                return exerciseList.indexOf(mom)
            }
        }
        return -1;
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun acceptDay(nameDay: String){
        daysToDo.replace(nameDay,true);
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun deleteDay(nameDay: String){
        daysToDo.replace(nameDay,false)
    }


}