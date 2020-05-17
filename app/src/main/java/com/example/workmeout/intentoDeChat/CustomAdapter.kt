package com.example.workmeout.intentoDeChat

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.R

class CustomAdapter(val listItem: ArrayList<FriendlyMessage>, val fromUserId: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var VIEW_HOLDER_ME: Int = 0
    var VIEW_HOLDER_YOU: Int = 1
    var listItemUpdated = listItem

    override fun getItemCount(): Int {

        return if (null != listItemUpdated) listItemUpdated!!.size else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (listItemUpdated!!.get(position).fromUserId.equals(fromUserId)) {
            VIEW_HOLDER_ME
        } else {
            VIEW_HOLDER_YOU
        }
    }



    override fun onBindViewHolder(v: RecyclerView.ViewHolder, pos: Int) {

        if (v is ViewHolderMe) { // Handle Image Layout
            val viewHolderImage = v as ViewHolderMe
            viewHolderImage.messageBody!!.setText(String.format("%s",listItemUpdated!!.get(pos).text))
            viewHolderImage.itemView.tag = viewHolderImage
        } else if (v is ViewHolderYou) { // Handle Video Layout
            val viewHolderYou = v as ViewHolderYou
            viewHolderYou.name!!.setText(String.format("%s", listItemUpdated!!.get(pos).type))
            viewHolderYou.messageBody!!.setText(String.format("%s", listItemUpdated!!.get(pos).text))
            val drawable = viewHolderYou.avatar!!.getBackground() as GradientDrawable
            drawable.setColor(Color.GRAY)
            viewHolderYou.itemView.tag = viewHolderYou
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {

            VIEW_HOLDER_ME -> return ViewHolderMe(
                LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.intento_de_chat_my_message,
                    parent,
                    false
                )
            )

            else  -> return ViewHolderYou(
                LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.intento_de_chat_their_message,
                    parent,
                    false
                )
            )
        }
        return return viewHolder
    }

    fun insertMessage(mFMessage: FriendlyMessage) {
        this.listItemUpdated.add(mFMessage)
        this.notifyItemInserted(listItemUpdated.size - 1 )

    }

    fun submitStuff(mFMessages: ArrayList<FriendlyMessage>) {


        for((index, message)  in mFMessages.withIndex()){
            if(index >= listItemUpdated.size){
                this.insertMessage(message)
            }

        }

    }

    class ViewHolderMe(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val messageBody = itemView?.findViewById<TextView>(R.id.message_body)
    }

    inner class ViewHolderYou(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val avatar = itemView?.findViewById<View>(R.id.avatar)
        val name = itemView?.findViewById<TextView>(R.id.name)
        val messageBody = itemView?.findViewById<TextView>(R.id.message_body)
    }
}