package com.example.booksale

import ItemViewAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoginActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemViewAdapter
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage_activity)

        recyclerView = findViewById(R.id.list)
        adapter = ItemViewAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)


    }

}