package com.example.workmeout.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.workmeout.R

class ExerciseDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        setTitle("Exercise Details")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_detail)
    }
}
