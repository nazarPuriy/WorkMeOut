package com.example.workmeout.ui.talk

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.workmeout.R
import com.example.workmeout.intentoDeChat.Chat
import com.example.workmeout.model.User2
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*
import kotlin.collections.ArrayList

class DataChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<User2> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BlogViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_blog_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){

            is BlogViewHolder ->{
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(list: List<User2>){
        items = list
    }

    class BlogViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.blog_image
        val name:TextView = itemView.blog_title
        val lastMessage = itemView.blog_author
        val card: CardView = itemView.card_chat
        var storageRef: StorageReference = FirebaseStorage.getInstance().reference


        fun bind(usuario: User2) {
            name.text = usuario.name//variable nombre
            lastMessage.text = usuario.email//variable correo

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(usuario.image)//variable imagen
                .into(image)

            storageRef.child("images/"+usuario.uid).downloadUrl.addOnSuccessListener {
                try {
                    Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions).load(it).into(image)
                }catch (e:Exception){
                    Toast.makeText(itemView.context, e.message, Toast.LENGTH_SHORT).show()
                }
            }


            /*Listener de los chats inspirado en el de Exercise*/
            card.setOnClickListener(View.OnClickListener {
                val intent: Intent = Intent(itemView.context, Chat::class.java)
                intent.putExtra("uid_friend", usuario.uid)
                intent.putExtra("name_friend", usuario.name)
                intent.putExtra("email_friend", usuario.email)
                itemView.context.startActivity(intent)
            })
        }
    }

}