package com.example.workmeout.Controlador

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.workmeout.model.User
import com.example.workmeout.dataBase.BaseDatos
import com.example.workmeout.ui.MainActivity
import com.example.workmeout.ui.identification.RegisterActivity


object Controlador{

    var baseDatos : BaseDatos = BaseDatos()
    var currentUser : User? = null

    //Guarda un usuario con los datos introducidos en la base de datos.
    fun register(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        baseDatos.guardarUsuario(context,username,name,password,email,phone,age,gender,weight,height)
    }

    //Hace una petición de loguear el usuario. La base de datos llamará la función loguear una vez haya respuesta o un Timeout.
    fun loginRequest(context : Context,username:String,password:String){
        currentUser = null //Nos asseguramos de que no haya ningún usuario activo.
        baseDatos.buscarUsuario(context,username,password)
    }

    fun editarUsuario(context: Context, username : String, name : String, password: String, email: String, phone : String, age : String, gender : String, weight : String, height : String){
        baseDatos.editarUsuario(context,username,name,password,email,phone,age,gender,weight,height)
    }

    //Loguea el usuario. Es llamada des de la base de datos.
    fun login(context:Context,password:String){
        if (currentUser == null) {
            Toast.makeText(context, "Incorrect username", Toast.LENGTH_SHORT).show()
        } else {
            if (currentUser!!.password != password) {
                Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show()
            } else {
                //Accedemos ya dentro de la aplicación.
                val logInt= Intent(context, MainActivity::class.java)
                context.startActivity(logInt)
            }
        }
    }

    //Comprueba si los datos introducidos són correctos.
    fun checkData(username : String, name : String, password: String, email: String, phone : String, age : String) : Boolean{
        //TODO crear objecte per comprovar les dades i comprovar aquí.
        //En el nombre de usuario no importan mayúsculas i minúsculas para loguearse. Eso si si se imprime se imprimirá con estas.
        return true
    }






}