package com.example.workmeout.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.workmeout.model.Exercise;

class Routine(name:String, days:String){

    var id = 0;
    var name = name
    var days = days
    var exerciseList: ArrayList<Exercise> = ArrayList()
    var ticks: List<Boolean> = ArrayList()
    var daysToDo: HashMap<String,Boolean> = mapOf(Pair("Monday",false),
        Pair("Tuesday",false),Pair("Wednesday",false),Pair("Thursday",false),Pair("Friday",false),Pair("Saturday",false),Pair("Sunday",false)) as HashMap<String, Boolean>


    fun getTotalReps(): Int{

        var total = 0

        for(exercise in exerciseList){
            total += exercise.reps
        }

        return total
    }

    fun getDoneReps(): Int{

        var done = 0

        for(exercise in exerciseList){
            if(exercise.done){
                done += exercise.reps
            }
        }

        return done
    }

    fun addExercise(ex: Exercise){
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
            var mom : Exercise = exerciseListIterator.next()
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