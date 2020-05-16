package com.example.workmeout.ui.identification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.intentoDeChat.FChat
import com.example.workmeout.intentoDeChat.FireHelper
import com.example.workmeout.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextUsername = findViewById(R.id.edttxt_user_login)
        editTextPassword = findViewById(R.id.edttxt_password_login)


    }

     //Function called when login button is clicked
    fun loginBtn(view: View){
        if(editTextPassword.text.isEmpty() || editTextUsername.text.isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
        }else {
            Controlador.loginRequest(view.context,editTextUsername.text.toString(),editTextPassword.text.toString())
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
}
