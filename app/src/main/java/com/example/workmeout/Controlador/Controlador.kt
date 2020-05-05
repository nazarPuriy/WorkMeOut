package com.example.workmeout.Controlador

import android.content.Context
import com.example.workmeout.model.User
import com.example.workmeout.dataBase.BaseDatos
object Controlador{

    var baseDatos : BaseDatos = BaseDatos()
    lateinit var currentUser : User


    fun register(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        if(true){ //Mirem si les dades tenen el format desitjat i que no estigui usari a la base de dades.
               baseDatos.guardarUsuario(context,username,name,password,email,phone,age,gender,weight,height)
        }

    }





}