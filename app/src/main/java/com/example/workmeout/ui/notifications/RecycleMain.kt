package com.example.workmeout

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class RecycleMain : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var list: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycle_view_x)
        recyclerView = findViewById(R.id.recyclerview)
        layoutManager= LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        list = resources.getStringArray(R.array.android_versions).toList().toTypedArray()
        viewAdapter = RecyclerAdapter(list)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = viewAdapter
    }
}