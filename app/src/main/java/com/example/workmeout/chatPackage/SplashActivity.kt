package com.example.workmeout.chatPackage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workmeout.ui.identification.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity

//por si quremos que cuando ya se haya iniciado sesion cada vez que entremos en la app comencemos directamente con la sesion iniciada
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser == null)
            startActivity<LoginActivity>()
        else
            startActivity<LoginActivity>()
        finish()
    }
}