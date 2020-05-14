package com.example.workmeout.model

import java.util.*
import kotlin.collections.ArrayList

class Exercise(id:Int, classId:Int, name:String, reps:Int, description: String, weight: Int, days:ArrayList<Date>, weights:ArrayList<Int>) {

    var id: Int = id
    var classId: Int = classId
    var name: String = name
    var description: String = description
    var reps: Int = reps
    var weight: Int = weight
    var days: ArrayList<Date> = days
    var weights: ArrayList<Int> = weights
    lateinit var routine: Routine

}