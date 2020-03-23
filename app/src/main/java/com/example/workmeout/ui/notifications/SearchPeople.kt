package com.example.workmeout.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.workmeout.R
import com.example.workmeout.ui.identification.RegisterActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchPeople : AppCompatActivity() {
    /*
    private lateinit var mySearchView: SearchView
    private lateinit var myList: ListView

    private lateinit var list: ArrayList<String>
    private lateinit var adapter_: ArrayAdapter<String>
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        */
        /*
        mySearchView = findViewById(R.id.search_view)
        myList = findViewById(R.id.myList)

        list.add("Maria")
        list.add("Pablo")
        list.add("Paula")
        list.add("Carlos")
        list.add("Pascual")
        list.add("Eugenia")
        list.add("Carmen")
        list.add("Jeremias")
        list.add("Nacho")

        adapter_ = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
        myList.adapter = adapter_
        */

        val goBackbttn = findViewById<FloatingActionButton>(R.id.goBack_button)
        goBackbttn.setOnClickListener {
            //Toast.makeText(context, "search button", Toast.LENGTH_SHORT).show()
            val goBack : Intent = Intent(this,NotificationsFragment::class.java)
            startActivity(goBack)
        }

    }

}