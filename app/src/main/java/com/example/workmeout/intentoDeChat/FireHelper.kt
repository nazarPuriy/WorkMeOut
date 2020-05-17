package com.example.workmeout.intentoDeChat

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


object FireHelper {

    private var mAuth: FirebaseAuth? = null
    private var mUser: FirebaseUser? = null
    private var db: FirebaseFirestore? = null

    fun AuthInit(): FirebaseAuth {

        mAuth = FirebaseAuth.getInstance()

        return mAuth!!

    }

    fun FirebaseInit(): FirebaseFirestore {

        db = FirebaseFirestore.getInstance()

        return db!!

    }

    fun getCurrentUser(): FirebaseUser {
        mUser = FirebaseAuth.getInstance().currentUser
        return mUser!!
    }

}