package com.example.workmeout.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.collections.ArrayList
import java.time.LocalDateTime

var calendar : Calendar = Calendar.getInstance();
class Exercise {

    var id:Int = 0
    var currentWeight:Int = 0
    var name: String = "name"
    var reps: Int = 0
    var done: Boolean = false
    var history: ArrayList<ExerciseEvent>

    constructor(id:Int, currentWeight: Int, name: String, reps: Int, done: Boolean){
        this.id = id
        this.currentWeight = currentWeight
        this.name = name
        this.reps = reps
        this.done = done
        this.history = ArrayList()
        history.add(ExerciseEvent(Date(), currentWeight))
    }

}