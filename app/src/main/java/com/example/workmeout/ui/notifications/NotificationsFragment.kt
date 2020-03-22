package com.example.workmeout.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workmeout.R
import com.example.workmeout.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_notifications.*


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    private lateinit var blogAdapter: BlogRecyclerAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        addDataSet()
        initRecycleView(root)



        return root





    }

    private fun addDataSet() {
        val data = DataSource.createDataSet()
        blogAdapter = BlogRecyclerAdapter()
        blogAdapter.submitList(data)
    }

    private fun initRecycleView(root:View) {
        root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(root.context)
            adapter = blogAdapter
        }
    }
}
