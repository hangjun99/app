package com.example.booksale

import ItemViewAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemViewAdapter
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage_activity)

        // 등록된 상품을 홈 화면에 보여주기
        recyclerView = findViewById(R.id.list)
        adapter = ItemViewAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)

        //하단 버튼 눌렀을 때 화면 전환
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        LoginActivity()
                        // Respond to navigation item 1 click
                    }
                    R.id.booksale -> {
                        bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        BookInsertActivity()
                        // Respond to navigation item 2 click
                    }
                    R.id.search -> {
                        bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        // Respond to navigation item 3 click
                    }
                    R.id.chatList ->{
                        bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                        ChatlistActivity()
                    }
                    else -> {
                        bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                        MainActivity()
                    }
                }
            true
        }
        bottomNavigation.selectedItemId = R.id.home
    }


    }


