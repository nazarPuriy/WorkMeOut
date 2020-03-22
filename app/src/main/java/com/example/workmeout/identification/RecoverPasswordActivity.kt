package com.example.workmeout.identification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.workmeout.R

class RecoverPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)
    }


    //TODO
    fun sendMessage(view: View){
        if(checkMail()){
            finish()
        }else{
            Toast.makeText(this,"MAIL NOT FOUND",Toast.LENGTH_SHORT).show()
        }
    }

    //Checks if the mail has an user associated. TODO
    private fun checkMail() : Boolean{
        return true
    }
}
