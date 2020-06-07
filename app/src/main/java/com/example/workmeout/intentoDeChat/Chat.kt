package com.example.workmeout.intentoDeChat

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.intentoDeChat.CustomAdapter
import com.example.workmeout.intentoDeChat.FireHelper
import com.example.workmeout.intentoDeChat.FriendlyMessage
import com.google.firebase.database.*
import java.util.ArrayList
import com.example.workmeout.R
import com.google.firebase.firestore.FirebaseFirestore

class Chat : AppCompatActivity(), View.OnClickListener{


    private lateinit var mAdapter: CustomAdapter

    //private var database: FirebaseDatabase? = null
    private var db: FirebaseFirestore? = null
    //private var myRef: DatabaseReference? = null

    private var fromUseridentify: String? = null
    private var mFMessages: ArrayList<FriendlyMessage>? = ArrayList()
    private var currentUser: String? = null

    private var mRecyclerView: RecyclerView? = null
    private var msgBtn: ImageButton? = null

    private var msgText: EditText? = null

    private var chatIndex: String = ""

    private var uidFriend: String = ""
    private var uidFriendArray: ArrayList<String> = ArrayList<String>()
    private var emailFriend: String = ""
    private var nameFriend: String = ""


    private var currentUserEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intento_de_chat_chat)

        msgBtn = findViewById(R.id.msgsend)
        msgBtn!!.setOnClickListener(this)

        msgText = findViewById(R.id.msgmessgaeedit)

        mRecyclerView = findViewById(R.id.recyclerview) as RecyclerView
        val mLinearLayoutManager = LinearLayoutManager(this)
        mRecyclerView!!.setLayoutManager(mLinearLayoutManager)


        val user = FireHelper.getCurrentUser()
        user.getDisplayName()
        user.getEmail()

        fromUseridentify = user.uid
        currentUser = user.displayName
        currentUserEmail = user.email!!
        uidFriend = intent.getStringExtra("uid_friend")
        nameFriend = intent.getStringExtra("name_friend")
        emailFriend = intent.getStringExtra("email_friend")

        val lengthThenNatural = compareBy<String> { it.length }
            .then(naturalOrder())

        val sortedUsers =
            listOf(uidFriend, fromUseridentify!!).sortedWith(lengthThenNatural)
        chatIndex = sortedUsers[0] + "@" + sortedUsers[1]

        mAdapter = CustomAdapter(mFMessages!!, fromUseridentify!!)
        mAdapter!!.submitStuff(ArrayList())
        mRecyclerView!!.adapter = mAdapter


        mFMessages = ArrayList()

        // Write a message to the database
        //database = FirebaseDatabase.getInstance()
        //myRef = database!!.getReference("message")
        db = FirebaseFirestore.getInstance()

        mHandler = Handler()
        startRepeatingTask()

    }

    override fun onDestroy() {
        super.onDestroy()
        stopRepeatingTask()
    }

    private fun getTimeStamp(): String {
        val tsLong = System.currentTimeMillis() / 1000
        return tsLong.toString()
    }

    fun updateFetchMessage() {
        fetchMessage()
    }

    private val mInterval = 1000 // 1 seconds by default, can be changed later
    private var mHandler: Handler? = null

    internal var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            try {

                updateFetchMessage()

            } finally {

                mHandler!!.postDelayed(this, mInterval.toLong())
            }
        }
    }

    internal fun startRepeatingTask() {
        mStatusChecker.run()
    }

    internal fun stopRepeatingTask() {
        mHandler!!.removeCallbacks(mStatusChecker)
    }

    fun fetchMessage() {
        /*
        database!!.getReference().child("message").addListenerForSingleValueEvent(
            object : ValueEventListener {


                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    mFMessages = ArrayList()

                    for (ds in dataSnapshot.children) {
                        val fromUserId = ds.child("fromUserId").getValue(String::class.java)
                        val name = ds.child("name").getValue(String::class.java)
                        val text = ds.child("text").getValue(String::class.java)
                        val timestamp = ds.child("timeStamp").getValue(String::class.java)
                        Log.d("TAG", "$fromUserId / $name / $text / $timestamp")
                        mFMessages!!.add(FriendlyMessage(text, name, timestamp, fromUserId))
                    }

                    if (mFMessages!!.size > 0) {

                        mAdapter = CustomAdapter(mFMessages!!, fromUseridentify!!)
                        mRecyclerView!!.setAdapter(mAdapter)
                        mRecyclerView!!.scrollToPosition(mRecyclerView!!.getAdapter()!!.itemCount - 1)
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("", "getUser:onCancelled", databaseError.toException())
                }


            })*/
        //database!!.getReference().child("message").addListenerForSingleValueEvent(



        val docRef = db!!.collection(chatIndex)
        mFMessages!!.clear()//TODO
        docRef.orderBy("timeStamp").get().addOnSuccessListener { result ->
            for (document in result) {
                val fromUserId = document.getString("fromUserId")
                val type = document.getString("type")
                val text = document.getString("text")
                val timestamp = document.getString("timeStamp")
                Log.d("TAG", "$fromUserId / $type / $text / $timestamp")
                mFMessages!!.add(FriendlyMessage(text, type, timestamp, fromUserId))
            }
            if (mFMessages!!.size > 0) {


                var new = false
                if(mFMessages!!.size > mAdapter.listItemUpdated.size){
                    new = true
                }

                mAdapter!!.submitStuff(mFMessages!!)

                if(new){
                    mRecyclerView!!.smoothScrollToPosition(mAdapter.listItemUpdated.size - 1)
                }
            }
        }
            .addOnFailureListener { exception ->
                Log.d("no_exist", "Error getting documents: ", exception)
            }
    }

    private fun subStringName(str: String): String {

        val subString = str.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            ?: return ""

        return subString[0]
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {

            R.id.msgsend -> {


                if (msgText!!.getText() == null || msgText!!.getText().length <= 0) return

                val friendlyMessage = FriendlyMessage(
                    msgText!!.getText().toString().trim { it <= ' ' },
                    currentUser,
                    getTimeStamp(),
                    fromUseridentify
                )
                val docRef = db!!.collection(chatIndex).add(friendlyMessage)
                    .addOnSuccessListener {
                        Log.w("", " Write was successful!")
                        msgText!!.setText("")

                        //para que solo se muestren los chats con mensajes
                        val userReceiver = db!!.collection("users").document(uidFriend!!)
                            .collection("friends").document(fromUseridentify!!)

                        userReceiver
                            .set(FChat(currentUserEmail!! ,currentUser!!,fromUseridentify!!))
                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }

                        val userSender = db!!.collection("users").document(fromUseridentify!!)
                            .collection("friends").document(uidFriend!!)

                        userSender
                            .set(FChat(emailFriend!! ,nameFriend!!,uidFriend!!))
                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
                        /*
                        val usuarioActual = db!!.collection("users").document(fromUseridentify!!)

                        usuarioActual
                            .update("receiverUid", uidFriend)
                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }*/
                    }
                    .addOnFailureListener {
                        // Write failed
                        // ...
                        Log.w("", " Write was failed!")
                    }
                /*
                myRef!!.push().setValue(friendlyMessage).addOnSuccessListener {
                    // Write was successful!
                    // ...
                    Log.w("", " Write was successful!")

//                    if (mAdapter == null) {
//                        mAdapter = CustomAdapter(mFMessages!!, fromUseridentify!!)
//                        mRecyclerView!!.setAdapter(mAdapter)
//                        mRecyclerView!!.scrollToPosition(mRecyclerView!!.getAdapter()!!.itemCount - 1)
//                    } else {
//
//                        mFMessages!!.add(
//                            FriendlyMessage(
//                                msgText!!.getText().toString().trim { it <= ' ' },
//                                currentUser,
//                                fromUseridentify,
//                                getTimeStamp()
//                            )
//                        )
//                        mAdapter!!.notifyItemRangeChanged(mFMessages!!.size, 1)
//                        mRecyclerView!!.scrollToPosition(mRecyclerView!!.getAdapter()!!.itemCount - 1)
//                    }
                }.addOnFailureListener {
                    // Write failed
                    // ...
                    Log.w("", " Write was failed!")
                }*/
            }
        }
    }

}