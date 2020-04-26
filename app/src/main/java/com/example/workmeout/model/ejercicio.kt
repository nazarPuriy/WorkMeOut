package com.example.workmeout.model

import java.util.*
import kotlin.collections.ArrayList

class ejercicio {
    var id: Int
    var name: String
    var typeExercise: String
    var fecha: Date
    var weight: Int
    var repetitions: Int

    constructor(id: Int, name: String, typeExercise: String,fecha: Date, weight: Int, repetitions: Int){
        this.id = id;
        this.name = name;
        this.typeExercise = typeExercise;
        this.fecha = fecha;
        this.weight = weight;
        this.repetitions = repetitions;
    }

    constructor(id: Int, name: String, weight: Int){
        this.id = id;
        this.name = name;
        this.weight = weight;
        val calendar: Calendar = Calendar.getInstance();
        fecha = calendar.time; //Data d'avui.
        typeExercise = "";
        repetitions = 0;
    }
}