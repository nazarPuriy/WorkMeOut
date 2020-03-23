package com.example.workmeout.ui.identification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.workmeout.MainActivity
import com.example.workmeout.R

class Login_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_)
    }

     //Function called when login button is clicked
    fun loginBtn(view: View){
         if(checkLogin()) {
             val logInt= Intent(this, MainActivity::class.java)
             startActivity(logInt)
         }
     }

    //Function called when register button is clicked
    fun registerBtn(view:View){
        val regInt= Intent(this, RegisterActivity::class.java)
        startActivity(regInt)
    }

    //Function called when forgotPassword button is clicked
    fun forgonPasswordBtn(view:View){
        val regFInt = Intent(this,
            RecoverPasswordActivity::class.java)
        startActivity(regFInt)
    }

    //TODO
    /***
     * Habría que ver que las casilla no esten vacias al principio.
     * Luego habría que comprovar en la base de datos las cosas.
     * Finalmente meterle al intent de la pantalla siguiente el nombre del usuario o su id.
     */
    private fun checkLogin() : Boolean{
        return true
    }
}
