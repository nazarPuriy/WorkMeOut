package com.example.workmeout.identification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.workmeout.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    //TODO
    fun register(view: View){
        if(checkData()){
            finish()
        }
    }

    //Check if the given arguments are correct. Makes a toast if there is any error.
    private fun checkData() : Boolean{
        //TODO
        return true
    }
}
