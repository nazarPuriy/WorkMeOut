package com.example.workmeout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Login_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_)
    }

     //Function called when login button is clicked
    fun loginBtn(view: View){
         if(checkLogin()) {
             val logInt: Intent = Intent(this, MainActivity::class.java)
             startActivity(logInt)
         }
     }

    //Function called when register button is clicked
    fun registerBtn(view:View){
        val regInt: Intent = Intent(this, RegisterActivity::class.java)
        startActivity(regInt)
    }

    //TODO
    private fun checkLogin() : Boolean{
        return true
    }
}
