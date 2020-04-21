package com.example.workmeout.ui.identification

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.EditTextPreference
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.workmeout.R
import com.example.workmeout.ui.MainActivity
import com.example.workmeout.util.FirestoreUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class RegisterActivity : AppCompatActivity() {

    /*Nacho's doing
    private val RC_SIGN_IN = 1

    private val signInProviders =
        listOf(
            AuthUI.IdpConfig.EmailBuilder()
            .setAllowNewAccounts(true)
            .setRequireName(true)
            .build())
    -------------*/
    //inicio
    private lateinit var editTextName: EditText
    private lateinit var editTextGmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextAge: EditText
    private lateinit var radioButtonGender: RadioButton

    private lateinit var buttonRegister: Button

    private var name:String = ""
    private var email:String = ""
    private var password:String = ""
    private var phone:String = ""
    private var age:String = ""

    private lateinit var mAuth:FirebaseAuth
    //private lateinit var mDataBase:DatabaseReference
    private lateinit var mDataBase:FirebaseFirestore

    companion object {
        private val TAG = "ClassName"
    }
    //fin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //inicio
        mAuth = FirebaseAuth.getInstance()
        //mDataBase = FirebaseDatabase.getInstance().reference
        mDataBase = FirebaseFirestore.getInstance()

        editTextName = findViewById(R.id.edttxt_name)
        editTextGmail = findViewById(R.id.edttxt_mail)
        editTextPassword = findViewById(R.id.edttxt_password)
        editTextPhone = findViewById(R.id.edttxt_phone)
        editTextAge = findViewById(R.id.edttxt_age)
        buttonRegister = findViewById(R.id.btn_register)


        buttonRegister.setOnClickListener {
            name = editTextName.text.toString()
            email = editTextGmail.text.toString()
            password = editTextPassword.text.toString()
            phone = editTextPhone.text.toString()
            age = editTextPassword.text.toString()

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phone.isEmpty() && !age.isEmpty()) {

                if (password.length >= 4) {
                    registerUser()
                } else {
                    Toast.makeText(
                        this,
                        "El password debe tener al menos 6 carácteres",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Completa todos los campos si us plau", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        //fin
    }

    //inicio
    private fun registerUser() {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                val user: HashMap<String, Any> = HashMap()
                user["name"] = name
                user["email"] = email
                user["password"] = password
                user["phone"] = phone
                user["age"] = age

                val uid: String = mAuth.currentUser!!.uid

                mDataBase.collection("users").document(user["name"] as String)
                    .set(user)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                verificationEmail()
                finish()
                /*
                mDataBase.child("Users").child(uid).setValue(user)
                    .addOnCompleteListener(this) { task2 ->
                        if (task2.isSuccessful) {
                            finish()

                        } else {
                            Toast.makeText(
                                this,
                                "Hubo un problema al crear los datos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }*/
            } else {
                Toast.makeText(this, "No se ha creado el usuario", Toast.LENGTH_SHORT).show()
            }

        }
    }
    //fin


    private fun verificationEmail()  {
        val user = mAuth.currentUser

        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email enviado.")
                }
            }
    }
/*
    //TODO
    fun register(view: View){
        if(checkData()){
            /*Nacho's doing
            val intent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(signInProviders)
                .build()
            startActivityForResult(intent, RC_SIGN_IN)
            -------------*/
            finish() //Nacho commented this but it was Nazar's
        }
    }*/

    //Check if the given arguments are correct. Makes a toast if there is any error.
    private fun checkData() : Boolean{
        //TODO
        return true
    }

    /*Nacho's doing
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                val progressDialog = indeterminateProgressDialog("Setting up your account")
                startActivity(intentFor<MainActivity>().newTask().clearTask())
                progressDialog.dismiss()

            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                if (response == null) return

                when (response.error?.errorCode) {
                    ErrorCodes.NO_NETWORK ->
                        register_layout.longSnackbar("No network")
                    ErrorCodes.UNKNOWN_ERROR ->
                        register_layout.longSnackbar("Unknown error")
                }
            }
        }
    }
    --------*/
}
