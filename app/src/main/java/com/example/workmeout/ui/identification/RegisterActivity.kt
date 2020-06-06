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
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import com.example.workmeout.ui.MainActivity
import com.example.workmeout.util.FirestoreUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
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

    private lateinit var editTextUsername : EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextGmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextPasswordConfirm: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextAge: EditText
    private lateinit var checkMale: RadioButton
    private lateinit var checkFemale: RadioButton
    private lateinit var buttonRegister: Button

    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDataBase:FirebaseFirestore
    companion object {
        private val TAG = "RegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextUsername = findViewById(R.id.edttxt_username)
        editTextName = findViewById(R.id.edttxt_name)
        editTextGmail = findViewById(R.id.edttxt_mail)
        editTextPassword = findViewById(R.id.edttxt_password)
        editTextPasswordConfirm = findViewById(R.id.edttxt_passwordConfirm)
        editTextPhone = findViewById(R.id.edttxt_phone)
        editTextAge = findViewById(R.id.edttxt_age)
        buttonRegister = findViewById(R.id.btn_register)
        checkMale = findViewById(R.id.rbtn_male)
        checkFemale = findViewById(R.id.rbtn_female)


        rellenar()



        //NACHO ABAJO
        //inicio

        mAuth = FirebaseAuth.getInstance()
        mDataBase = FirebaseFirestore.getInstance()
    }

    //TODO borrar es dummy
    fun rellenar(){
        editTextUsername.setText("juan")
        editTextName.setText("JUAN POCO")
        editTextPassword.setText("Contra")
        editTextPasswordConfirm.setText("Contra")
        editTextGmail.setText("dummymail@hotmail.com")
        editTextPhone.setText("647327222")
        editTextAge.setText("20")
    }

    //Comprueba si los datos estan y si son correctos. EN caso de no serlo muestra un Toast.
    private fun checkData() : Boolean{
        //Miramos si toda la inforamción está presente.
        if(editTextName.text.isEmpty() || editTextUsername.text.isEmpty() || editTextPassword.text.isEmpty() ||
            editTextPasswordConfirm.text.isEmpty() || editTextGmail.text.isEmpty() || editTextPhone.text.isEmpty() ||
            editTextAge.text.isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return false
        }else{
            //Miramos que este seleccionado uno de los géneros disponibles.
            if(!checkFemale.isChecked && !checkMale.isChecked){
                Toast.makeText(this,"Select a gender",Toast.LENGTH_SHORT).show()
                return false
            }
        }
        //Miramos que las contraseñas coincidan.
        if(editTextPassword.text.toString() != editTextPasswordConfirm.text.toString()){
            Toast.makeText(this,"Password mismatch",Toast.LENGTH_SHORT).show()
            return false
        }

        return Controlador.checkData(editTextUsername.text.toString(),editTextName.text.toString(), editTextPassword.text.toString(), editTextGmail.text.toString(),editTextPhone.text.toString(),editTextAge.text.toString())
    }

    //Si los datos introducidos son correctos guarda el usuario correspondiente a estos en la base de datos.
    fun registerUser(view: View) {
        Controlador.register(view.context,editTextUsername.text.toString(),editTextName.text.toString(),editTextPassword.text.toString(),editTextGmail.text.toString(),editTextPhone.text.toString(), editTextAge.text.toString(),checkMale.isChecked.toString(),"0","0")

        /*Firebase------------------------------------------------*/
        mAuth.createUserWithEmailAndPassword(editTextGmail.text.toString(), "123456").addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user: HashMap<String, Any> = HashMap()
                user["email"] = editTextGmail.text.toString()
                user["name"] = editTextName.text.toString()
                user["username"] = editTextUsername.text.toString()
                user["uid"] = mAuth.currentUser!!.uid
                user["receiverUid"] = ""

                mDataBase.collection("users").document(user["uid"] as String)
                    .set(user)
                    .addOnSuccessListener {
                        Log.d(TAG, "DocumentSnapshot successfully written!")

                        //to update a name
                        val crrntUser = FirebaseAuth.getInstance().currentUser

                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(user["name"] as String?)
                            .build()

                        crrntUser?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "User profile updated.")
                                }
                            }
                        Toast.makeText(this, "Se ha creado el usuario", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                            e -> Log.w(TAG, "Error writing document", e)
                        Toast.makeText(this, "Error creando el usuario", Toast.LENGTH_SHORT).show()

                    }

                verificationEmail()
                finish()
            } else {
                Toast.makeText(this, "No se ha creado el usuario", Toast.LENGTH_SHORT).show()
            }
            Controlador.register(view.context,editTextUsername.text.toString(),editTextName.text.toString(),editTextPassword.text.toString(),editTextGmail.text.toString(),editTextPhone.text.toString(), editTextAge.text.toString(),checkMale.isChecked.toString(),"0","0")

        }
        /*---------------------------------------------------------*/
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
}
