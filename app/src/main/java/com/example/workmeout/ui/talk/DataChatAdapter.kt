package com.example.workmeout.ui.talk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.workmeout.R
import kotlinx.android.synthetic.main.layout_blog_list_item.view.*
import kotlin.collections.ArrayList

class DataChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<DataChat> = ArrayList()

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

    fun submitList(blogList: List<DataChat>){
        items = blogList
    }

    class BlogViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val blogImage: ImageView = itemView.blog_image
        val blogTitle:TextView = itemView.blog_title
        val blogAuthor = itemView.blog_author

        fun bind(dataChat: DataChat) {
            blogTitle.text = dataChat.title
            blogAuthor.text = dataChat.last_message

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(dataChat.image)
                .into(blogImage)
        }
    }

}