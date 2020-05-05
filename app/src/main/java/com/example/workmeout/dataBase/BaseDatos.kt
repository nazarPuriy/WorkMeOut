package com.example.workmeout.dataBase
import android.content.Context
import com.example.workmeout.dataBase.UserDataBase
import com.example.workmeout.model.User

class BaseDatos {
    var userDataBase : UserDataBase

    constructor(){
        userDataBase = UserDataBase()
    }

    fun guardarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        userDataBase.guardarUsuario(context,username,name,password,email,phone,age,gender,weight,height)
    }

    //Método que utilizamos para buscar usuarios en la base de datos.
    fun buscarUsuario(context: Context, username : String) : User?{
        return userDataBase.buscarUsuario(context,username)
    }

    //Método que utilizamos para sobreescribir información.
    fun editarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        userDataBase.editarUsuario(context,username,name,password,email,phone,age,gender,weight,height)
    }

}