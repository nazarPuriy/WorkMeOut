package com.example.workmeout.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.workmeout.model.Exercise;

class Routine(name:String, days:String){

    var id: Int = 0;
    var name:String = name
    var days: String = days
    var exerciseList: ArrayList<Exercise> = ArrayList()
    var ticks: List<Boolean> = ArrayList()
    var daysSet: HashSet<String> = createSetDays(days)

    fun createSetDays(days: String): HashSet<String>{
        var daysS : HashSet<String> = HashSet()
        if(days == ""){
            return daysS
        }else{
            var ini : Int = 0
            var fin : Int = 2
            while(fin<days.length){
                var newDay : String = days.substring(ini,fin);
                daysS.add(newDay)
                ini = ini + 5
                fin = fin + 5
            }
        }
        return daysS;
    }
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

    //TODO ADD DAY, REMOVE DAY ( OF THE SET )



}