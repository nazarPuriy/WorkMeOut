package com.example.workmeout.chatPackage.Item

import android.content.Context
import com.bumptech.glide.Glide
import com.example.workmeout.R

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person.*

// AppGlidModule.kt
import com.bumptech.glide.request.RequestOptions
import com.example.workmeout.model.UserOld
import com.example.workmeout.util.StorageUtil

class PersonItem(val person: UserOld,
                 val userId: String,
                 private val context: Context)
    : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_name.text = person.name
        viewHolder.textView_bio.text = person.bio
        if (person.profilePicturePath != null)
            Glide.with(context)
                .load(StorageUtil.pathToReference(person.profilePicturePath))
                .apply(RequestOptions()
                .placeholder(R.drawable.ic_account_circle_black_24dp))
                .into(viewHolder.imageView_profile_picture)
    }

    override fun getLayout() = R.layout.item_person
}