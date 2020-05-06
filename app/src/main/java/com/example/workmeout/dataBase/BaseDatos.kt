package com.example.workmeout.dataBase
import android.content.Context
import com.example.workmeout.model.User
import com.example.workmeout.ui.me.ExerciseSearchAdapter

class BaseDatos {
    var userDataBase : UserDataBase
    var exerciseDataBase : ExerciseDataBase
    constructor(){
        userDataBase = UserDataBase()
        exerciseDataBase = ExerciseDataBase()
    }

    fun guardarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        userDataBase.guardarUsuario(context,username,name,password,email,phone,age,gender,weight,height)
    }

    //Método que utilizamos para buscar usuarios en la base de datos.
    fun buscarUsuario(context: Context, username : String, password : String){
        return userDataBase.buscarUsuario(context,username,password)
    }

    //Método que utilizamos para sobreescribir información.
    fun editarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        userDataBase.editarUsuario(context,username,name,password,email,phone,age,gender,weight,height)
    }

    //Método que usaremos para guardar la descripción de un nuevo ejercicio
    fun guardarEjercicio(context: Context, name : String, description: String){
        exerciseDataBase.guardarEjercicio(context,name,description)
    }

    fun matchExercise(context: Context, name:String, adapter:ExerciseSearchAdapter){
        exerciseDataBase.matchExercise(context, name, adapter)
    }

}