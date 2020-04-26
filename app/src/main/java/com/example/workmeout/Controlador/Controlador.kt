package com.example.workmeout.Controlador

import com.example.workmeout.model.Usuari

object Controlador{
    lateinit var currentUser : Usuari


    fun login(email: String, password: String){
        var users : Usuari = Usuari(email,password);
        this.currentUser = users;

    }


}