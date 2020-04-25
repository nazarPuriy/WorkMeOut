package com.example.workmeout.ui.identification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.workmeout.R
import com.google.firebase.auth.FirebaseAuth

class RecoverPasswordActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var emailAddress:String//"user@example.com"

    private lateinit var mAuth: FirebaseAuth

    companion object {
        private val TAG = "ClassName"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        editTextEmail = findViewById(R.id.edttxt_mail_recover)
    }

    fun sendMessage(view: View){
        emailAddress = editTextEmail.text.toString()

        if (emailAddress.isEmpty()) {
            Toast.makeText(this,"Email address is empty",Toast.LENGTH_SHORT).show()
        }
        else if(true/*!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()*/){
            mAuth = FirebaseAuth.getInstance()

            mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"MAIL sended",Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this,"This mail is not in our DATABASE",Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(this,"MAIL NOT FOUND",Toast.LENGTH_SHORT).show()
        }

    }

    //Checks if the mail has an user associated. TODO
    private fun checkMail() : Boolean{
        return true
    }
}
