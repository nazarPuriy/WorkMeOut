package com.example.workmeout.ui.talk

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.workmeout.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.search_people.*
import java.util.*
import kotlin.collections.ArrayList

class SearchPeople : AppCompatActivity() {


    private lateinit var items: Array<String>
    private var listItems: ArrayList<String> = ArrayList()
    private lateinit var adapter_: ArrayAdapter<String>
    private lateinit var myList: ListView
    private lateinit var mySearchView: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_people)
        myList = findViewById(R.id.listview)
        mySearchView = findViewById(R.id.txtsearch)

        /*

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation adapter_ = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems)
        myList.adapter = adapter__dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/

        //mySearchView = findViewById(R.id.search_view)
        //myList = findViewById(R.id.myList)

        /*
        listItems.add("Maria")
        listItems.add("Pablo")
        listItems.add("Paula")
        listItems.add("Carlos")
        listItems.add("Pascual")
        listItems.add("Eugenia")
        listItems.add("Carmen")
        listItems.add("Jeremias")
        listItems.add("Nacho")
*/
        //initList()
        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val image = findViewById<ImageView>(R.id.imageView)
        mySearchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            /*
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Toast.makeText(this@SearchPeople, "random people", Toast.LENGTH_SHORT).show()
            }*/


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().equals("")) {
                    initList()
                    image.visibility = View.VISIBLE
                } else {
                    image.visibility = View.INVISIBLE
                    searchItem(s.toString())
                }
            }
        })

        val backbutton = findViewById<FloatingActionButton>(R.id.back_button)
        backbutton.setOnClickListener {
            //Toast.makeText(context, "search button", Toast.LENGTH_SHORT).show()
            finish()
        }
        /*
        fun goback(v: View){

        }
        fun backbutton(v: View){

        }*/

    }

    fun initList() {
        /*
        adapter_ = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems)
        myList.adapter = adapter_*/
        items = arrayOf("Maria", "Pablo", "Paula", "Carlos", "Pascual", "Eugenia",
            "Carmen", "Jeremias", "Nacho")
        listItems = ArrayList(items.size)
        listItems.addAll(items)
        adapter_ = ArrayAdapter<String>(this, R.layout.search_card, R.id.txtitem)
        listview.adapter = adapter_
    }

    fun searchItem(textToSearch: String) {

        for(item in items) {
            if(!item.contains(textToSearch)) {
                listItems.remove(item)
            }
        }

       adapter_.notifyDataSetChanged()
    }
}