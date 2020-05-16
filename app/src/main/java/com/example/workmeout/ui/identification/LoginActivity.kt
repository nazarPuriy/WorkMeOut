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
        editTextEmail = findViewById(R.id.edttxt_email_login)
        editTextUsername = findViewById(R.id.edttxt_user_login)
        editTextPassword = findViewById(R.id.edttxt_password_login)

        mAuth = FireHelper.AuthInit()
        db = FireHelper.FirebaseInit()
    }

     //Function called when login button is clicked
    fun loginBtn(view: View){
        if(editTextPassword.text.isEmpty() || editTextUsername.text.isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
        }else {

            mAuth.signInWithEmailAndPassword(
                editTextEmail.text.toString(),
                editTextPassword.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val message = FChat(
                            editTextEmail.text.toString(),
                            editTextUsername.text.toString(),
                            mAuth.currentUser!!.uid
                        )
                        db.collection("chat").add(message)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    baseContext, "Well writeen.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    baseContext, "Failed at modifying",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        /*
                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("chat")

                        val friendlyMessage = FChat(email, subStringName(email), mAuth!!.getCurrentUser()!!.uid)
                        myRef.push().setValue(friendlyMessage)
                        */
                        val logInt = Intent(this, MainActivity::class.java)
                        startActivity(logInt)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
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
