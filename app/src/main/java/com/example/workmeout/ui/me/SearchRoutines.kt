package com.example.workmeout.ui.me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.Controlador.Controlador
import com.example.workmeout.R
import org.jetbrains.anko.find

class SearchRoutines : AppCompatActivity() {

    lateinit var recyclerRoutines: RecyclerView
    lateinit var tvName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_routines)
        recyclerRoutines = findViewById(R.id.rv_routine_matches)
        tvName = findViewById(R.id.edt_name_routine)

        var adapter:RoutineSearchAdapter = RoutineSearchAdapter()
        adapter.submitList(ArrayList())
        recyclerRoutines.layoutManager = LinearLayoutManager(this)
        recyclerRoutines.adapter = adapter
        adapter.notifyDataSetChanged()
        Controlador.matchRoutine(baseContext, "", adapter)

        tvName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Controlador.matchRoutine(baseContext, tvName.text.toString(), adapter)

            }
        })


    }
}
