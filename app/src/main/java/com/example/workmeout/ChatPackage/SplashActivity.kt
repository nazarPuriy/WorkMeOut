package com.example.workmeout.ChatPackage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.workmeout.ui.identification.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity

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