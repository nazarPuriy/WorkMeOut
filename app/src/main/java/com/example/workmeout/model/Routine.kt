package com.example.workmeout.model

class Routine {
    //Todos los atributos del usuario
    var id : Int //Utilizaremos este como identificador único de la rutina del usuario.
    var classid : Int
    var name : String
    var description : String
    var days : Int
    var exercises: MutableList<Int>

    //Constructor principal de la clase usuario
    constructor(id : Int, classid: Int, name: String, description : String, days: Int){
        this.id = id
        this.classid = classid
        this.name = name
        this.description = description
        this.days = days
        exercises = mutableListOf() //Creamos la lista demomento sin ejercicios
    }


}