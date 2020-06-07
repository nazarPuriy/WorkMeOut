package com.example.workmeout.ui.talk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import android.widget.AdapterView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.workmeout.R
import com.example.workmeout.intentoDeChat.Chat
import com.example.workmeout.intentoDeChat.FireHelper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_people.*
import com.firebase.ui.auth.AuthUI.getApplicationContext as getApplicationContext1


class SearchPeople : AppCompatActivity() {


    private lateinit var names: Array<String?>
    private lateinit var items2: Array<String?>
    //private var listItems: ArrayList<String> = ArrayList()
    private lateinit var adapter_: ArrayAdapter<String>
    private lateinit var myList: ListView
    private lateinit var mySearchView: EditText

    private lateinit var last_message: Array<String?>
    private lateinit var last_message2: Array<String?>

    private lateinit var mDataBase: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_people)

        /*
        items = arrayOf("Maria", "Pablo", "Paula", "Carlos", "Pascual", "Eugenia",
            "Carmen", "Jeremias", "Nacho")
        last_message = arrayOf("Maria, it's okey", "hahahah I guess", "Yeaaaaaah", "No, it is going to be impossible :(", "Richelle", "Maria is the best. Have you heard from her lately?", "I'm asking Alfonso about Maria", "Keep Calm hahahah", "Okey")
        */
        var listNames:ArrayList<String?> =  ArrayList()
        var listLast_message:ArrayList<String?> =  ArrayList()
        var listUid:ArrayList<String?> =  ArrayList()

        mDataBase = FirebaseFirestore.getInstance()
        val docRef = mDataBase.collection("users")
        val user = FireHelper.getCurrentUser()

        docRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("exist", "${document.id} => ${document.data}")

                    if(!user.getDisplayName().equals(document.getString("name"))) {
                        listNames.add(document.getString("name"))
                        listLast_message.add(document.getString("email"))
                        listUid.add(document.getString("uid"))
                    }

                }
                names = arrayOfNulls<String?>(listNames.size)
                listNames.toArray(names)
                last_message = arrayOfNulls<String?>(listLast_message.size)
                listLast_message.toArray(last_message)
                var adapter = MyAdapter(this, names, last_message)
                listview.adapter = adapter
                adapter.notifyDataSetChanged()


            }
            .addOnFailureListener { exception ->
                Log.d("no_exist", "Error getting documents: ", exception)
            }
        
        names = arrayOfNulls<String?>(listNames.size)
        listNames.toArray(names)
        last_message = arrayOfNulls<String?>(listLast_message.size)
        listLast_message.toArray(last_message)

        myList = findViewById<View>(R.id.listview) as ListView

        var adapter = MyAdapter(this, names, last_message)
        listview.adapter = adapter


        listview.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent: Intent = Intent(this, Chat::class.java)
            intent.putExtra("uid_friend", listUid[position])
            intent.putExtra("name_friend", listNames[position])
            intent.putExtra("email_friend", listLast_message[position])
            this.startActivity(intent)
        })

        mySearchView = findViewById(R.id.txtsearch)

        val image = findViewById<ImageView>(R.id.imageView)
        mySearchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().equals("")) {
                    adapter = MyAdapter(this@SearchPeople, names, last_message)
                    listview.adapter = adapter
                    image.visibility = View.INVISIBLE
                } else {
                    image.visibility = View.VISIBLE

                    var itemsTemp:ArrayList<String?> = ArrayList()
                    var lastMessageTemp:ArrayList<String?> = ArrayList()


                    for(i in 0..(names.size - 1)) {
                        var item = names[i]
                        var lastMessage = last_message[i]

                        if(item!!.toLowerCase().contains(s.toString().toLowerCase())) {
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

    internal class MyAdapter(
        c: Context,
        title: Array<String?>,
        description: Array<String?>
    ) :
        ArrayAdapter<String?>(c, R.layout.row_chat, R.id.textview_name_row, title) {
        var cont: Context
        var rTitle: Array<String?>
        var rDescription: Array<String?>
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