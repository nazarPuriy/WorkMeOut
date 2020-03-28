package com.example.workmeout.ui.talk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import android.widget.AdapterView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.workmeout.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_people.*
import com.firebase.ui.auth.AuthUI.getApplicationContext as getApplicationContext1


class SearchPeople : AppCompatActivity() {


    private lateinit var items: Array<String>
    private lateinit var items2: Array<String>
    private var listItems: ArrayList<String> = ArrayList()
    private lateinit var adapter_: ArrayAdapter<String>
    private lateinit var myList: ListView
    private lateinit var mySearchView: EditText

    private lateinit var last_message: Array<String>
    private lateinit var last_message2: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_people)

        items = arrayOf("Maria", "Pablo", "Paula", "Carlos", "Pascual", "Eugenia",
            "Carmen", "Jeremias", "Nacho")
        last_message = arrayOf("Maria, it's okey", "hahahah I guess", "Yeaaaaaah", "No, it is going to be impossible :(", "Richelle", "Maria is the best. Have you heard from her lately?", "I'm asking Alfonso about Maria", "Keep Calm hahahah", "Okey")

        myList = findViewById<View>(R.id.listview) as ListView
        // now create an adapter class

        // now create an adapter class
        var adapter = MyAdapter(this, items, last_message)
        listview.adapter = adapter
        // there is my mistake...
        // now again check this..

        // now set item click on list view
        // there is my mistake...
        // now again check this..

        // now set item click on list view
        listview.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                /*
                val buttonppl : Intent = Intent(applicationContext, SharedChat::class.java)
                startActivity(buttonppl)*/
                Toast.makeText(this@SearchPeople, "Chat", Toast.LENGTH_SHORT)
                    .show()
            }
            if (position == 1) {
                Toast.makeText(this@SearchPeople, "Chat", Toast.LENGTH_SHORT)
                    .show()
            }
            if (position == 2) {
                Toast.makeText(this@SearchPeople, "Chat", Toast.LENGTH_SHORT)
                    .show()
            }
            if (position == 3) {
                Toast.makeText(this@SearchPeople, "Chat", Toast.LENGTH_SHORT)
                    .show()
            }
            if (position == 4) {
                Toast.makeText(this@SearchPeople, "Chat", Toast.LENGTH_SHORT)
                    .show()
            }
            if (position == 5) {
                Toast.makeText(this@SearchPeople, "Chat", Toast.LENGTH_SHORT)
                    .show()
            }
            if (position == 6) {
                Toast.makeText(this@SearchPeople, "Chat", Toast.LENGTH_SHORT)
                    .show()
            }
            if (position == 7) {
                Toast.makeText(this@SearchPeople, "Chat", Toast.LENGTH_SHORT)
                    .show()
            }
            if (position == 8) {
                Toast.makeText(this@SearchPeople, "Chat", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        // so item click is done now check list view
    /*
        myList = findViewById(R.id.listview)
        mySearchView = findViewById(R.id.txtsearch)

        val image = findViewById<ImageView>(R.id.imageView)
        mySearchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().equals("")) {
                    initList()
                    image.visibility = View.VISIBLE
                } else {
                    image.visibility = View.INVISIBLE
                    initList()
                    searchItem(s.toString())
                }
            }
        })*/

        mySearchView = findViewById(R.id.txtsearch)

        val image = findViewById<ImageView>(R.id.imageView)
        mySearchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().equals("")) {
                    adapter = MyAdapter(this@SearchPeople, items, last_message)
                    listview.adapter = adapter
                    image.visibility = View.VISIBLE
                } else {
                    image.visibility = View.INVISIBLE

                    var itemsTemp:ArrayList<String> = ArrayList()
                    var lastMessageTemp:ArrayList<String> = ArrayList()


                    for(i in 0..(items.size - 1)) {
                        var item = items[i]
                        var lastMessage = last_message[i]

                        if(item.toLowerCase().contains(s.toString().toLowerCase())) {
                            itemsTemp.add(item)
                            lastMessageTemp.add(lastMessage)
                        }

                        items2 = itemsTemp.toArray(arrayOfNulls<String>(itemsTemp.size))
                        last_message2 = lastMessageTemp.toArray(arrayOfNulls<String>(lastMessageTemp.size))

                    }
                    adapter = MyAdapter(this@SearchPeople, items2, last_message2)
                    listview.adapter = adapter
                    adapter.notifyDataSetChanged()

                }



            }
        })

    }

    fun initList() {
        listItems = ArrayList(items.size)
        listItems.addAll(items)
        adapter_ = ArrayAdapter<String>(this, R.layout.search_card, R.id.txtitem)
        adapter_.addAll(listItems)
        listview.adapter = adapter_

    }

    fun searchItem(textToSearch: String) {
        initList()
        for(item in items) {
            if(!item.toLowerCase().contains(textToSearch.toLowerCase())) {
                listItems.remove(item)

            }
            adapter_.clear()
            adapter_.addAll(listItems)
            adapter_.notifyDataSetChanged()
        }

    }

    internal class MyAdapter(
        c: Context,
        title: Array<String>,
        description: Array<String>
    ) :
        ArrayAdapter<String?>(c, R.layout.row_chat, R.id.textview_name_row, title) {
        var cont: Context
        var rTitle: Array<String>
        var rDescription: Array<String>
        override fun getView(
            position: Int,
            @Nullable convertView: View?,
            parent: ViewGroup
        ): View {
            val layoutInflater : LayoutInflater =
               LayoutInflater.from(cont)
            val row: View = layoutInflater.inflate(R.layout.row_chat, parent, false)
            val myTitle = row.findViewById<TextView>(R.id.textview_name_row)
            val myDescription = row.findViewById<TextView>(R.id.textview_mes_row)

            // now set our resources on views
            myTitle.text = rTitle[position]
            myDescription.text = rDescription[position]
            return row
        }

        init {
            cont = c
            rTitle = title
            rDescription = description
        }
    }
}