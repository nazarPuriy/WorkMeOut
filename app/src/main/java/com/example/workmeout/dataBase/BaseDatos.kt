package com.example.workmeout.dataBase
import android.content.Context
import android.widget.Toast
import com.example.workmeout.model.User
import com.example.workmeout.ui.me.ExerciseSearchAdapter

class BaseDatos {
    var userDataBase : UserDataBase
    var exerciseDataBase : ExerciseDataBase
    var routineDataBase : RoutineDataBase
    constructor(){
        userDataBase = UserDataBase()
        exerciseDataBase = ExerciseDataBase()
        routineDataBase = RoutineDataBase()
    }

    fun guardarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        userDataBase.guardarUsuario(context,username,name,password,email,phone,age,gender,weight,height)
    }

    //Método que utilizamos para buscar usuarios en la base de datos.
    fun buscarUsuario(context: Context, username : String, password : String){
        return userDataBase.buscarUsuario(context,username,password)
    }

    //Método que utilizamos para sobreescribir información.
    fun editarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String, routine1 : Int, routine2 : Int, routine3 : Int, routine4 : Int, routine5 : Int){
        userDataBase.editarUsuario(context,username,name,password,email,phone,age,gender,weight,height,routine1,routine2,routine3,routine4,routine5)
    }

    //Método que usaremos para guardar la descripción de un nuevo ejercicio
    fun guardarEjercicio(context: Context, name : String, description: String){
        exerciseDataBase.guardarEjercicio(context,name,description)
    }

    //Métode que guarda una rutina a la base de dades general
    fun guardarRutina(
        context: Context,
        name: String,
        description: String,
        exercise1: Int,
        exercise2: Int,
        exercise3: Int,
        exercise4: Int,
        exercise5: Int,
        exercise6: Int,
        exercise7: Int,
        exercise8: Int,
        exercise9: Int,
        exercise10: Int,
        exercise11: Int,
        exercise12: Int,
        exercise13: Int,
        exercise14: Int,
        exercise15: Int,
        days : Int
    ) {
        routineDataBase.guardarRutina(context,name,description,exercise1,exercise2,exercise3,exercise4,exercise5,exercise6,exercise7,exercise8,exercise9,exercise10,exercise11,exercise12,exercise13,exercise14,exercise15,days)
    }

    //Métode que guarda una rutina a la base de datos de rutinas de usuario
    fun guardarRutinaUsuario(
        context: Context,
        classid: Int,
        days: Int,
        exercise1: Int,
        exercise2: Int,
        exercise3: Int,
        exercise4: Int,
        exercise5: Int,
        exercise6: Int,
        exercise7: Int,
        exercise8: Int,
        exercise9: Int,
        exercise10: Int,
        exercise11: Int,
        exercise12: Int,
        exercise13: Int,
        exercise14: Int,
        exercise15: Int
    ) {
        routineDataBase.guardarRutinaUsuario(context,classid,days,exercise1,exercise2,exercise3,exercise4,exercise5,exercise6,exercise7,exercise8,exercise9,exercise10,exercise11,exercise12,exercise13,exercise14,exercise15)
    }

    fun buscarRutinaUsuario(context: Context, id: Int){
        routineDataBase.buscarRutinaUsuario(context,id)
    }

    //Métode per buscar coincidencia amb la string
    fun matchExercise(context: Context, name:String, adapter:ExerciseSearchAdapter){
        exerciseDataBase.matchExercise(context, name, adapter)
    }

}