package com.example.workmeout.model

import java.util.*
import kotlin.collections.ArrayList

class ExerciseOLD (id:Int, currentWeight:Int, name:String, reps:Int, done:Boolean, fechaActual: Date){

    var id:Int = id
    var currentWeight:Int = currentWeight
    var name: String = name
    var reps: Int = reps
    var done: Boolean = done
    var fechaActual: Date = fechaActual
    var llistaDiesPes: ArrayList<exerciseEvent> = ArrayList()

    constructor(currentWeight: Int, name: String, reps: Int, done: Boolean) : this(0,currentWeight,name,reps,done, Calendar.getInstance().time)

    fun addExerciseEvent(ex: exerciseEvent){
        if(!isExerciseEvent(ex.fecha)){
            llistaDiesPes.add(ex)
        }else{
            //NOTIFICACION QUE NO SE PUEDE AÑADIR PORQUE YA ESTA
        }
    }

    fun replaceExerciseEvent(exFecha: Date, newWeight: Double){
        var positionExEv = exEvPosition(exFecha)
        var newExEv: exerciseEvent = exerciseEvent(exFecha,newWeight)
        if(positionExEv == -1){
            addExerciseEvent(newExEv)
        }else{
            llistaDiesPes[positionExEv] = newExEv
        }

    }

    fun deleteExerciseEvent(exFecha: Date){
        var positionEx = exEvPosition(exFecha);
        if(positionEx == -1){
            //Mensaje de que no existe el ejercicio i no se puede eliminar
        }else{
            llistaDiesPes.removeAt(positionEx)
        }
    }

    fun isExerciseEvent(exFecha: Date): Boolean {
        val llistaDiesPesIterator = llistaDiesPes.iterator()
        while(llistaDiesPesIterator.hasNext()){
            if(llistaDiesPesIterator.next().fecha == exFecha)
                return true
        }
        return false
    }

    fun exEvPosition(exFecha: Date): Int{
        val llistaDiesPesIterator = llistaDiesPes.iterator()
        while(llistaDiesPesIterator.hasNext()){
            var mom : exerciseEvent = llistaDiesPesIterator.next()
            if(mom.fecha == exFecha){
                return llistaDiesPes.indexOf(mom)
            }
        }
        return -1;
    }
}