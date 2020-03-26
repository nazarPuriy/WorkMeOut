package com.example.workmeout.model

class Routine(name:String, days:String){

    var name = name
    var days = days
    var exercises: List<Exercise> = ArrayList()
    var ticks: List<Boolean> = ArrayList()

    fun getTotalReps(): Int{

        var total = 0

        for(exercise in exercises){
            total += exercise.reps
        }

        return total
    }

    fun getDoneReps(): Int{

        var done = 0

        for(exercise in exercises){
            if(exercise.done){
                done += exercise.reps
            }
        }

        return done
    }

}