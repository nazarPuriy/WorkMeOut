package com.example.workmeout.model

import java.util.*
var calendar : Calendar = Calendar.getInstance();
class Exercise (id:Int, currentWeight:Int, name:String, reps:Int, done:Boolean, fecha: Date){

    var id:Int = id
    var currentWeight:Int = currentWeight
    var name: String = name
    var reps: Int = reps
    var done: Boolean = done
    var fecha: Date = fecha;

    constructor(currentWeight: Int, name: String, reps: Int, done: Boolean) : this(0,currentWeight,name,reps,done,
        calendar.time)

}