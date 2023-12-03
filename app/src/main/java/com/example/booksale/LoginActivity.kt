package com.example.booksale

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginActivity : AppCompatActivity(){
    //private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_booksale)

<<<<<<< HEAD
        // 등록된 상품을 홈 화면에 보여주기
        recyclerView = findViewById(R.id.list)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                    Toast.makeText(applicationContext, "home", Toast.LENGTH_SHORT).show()
                    // Respond to navigation item 1 click
                }
                R.id.fragment_booksale -> {
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    Toast.makeText(applicationContext, "booksale", Toast.LENGTH_SHORT).show()
                    // Respond to navigation item 2 click
                }
                R.id.fragment_search -> {
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    Toast.makeText(applicationContext, "검색", Toast.LENGTH_SHORT).show()
                    // Respond to navigation item 3 click
                }
                R.id.fragment_chatList->{
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    Toast.makeText(applicationContext, "챗", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ChatlistActivity::class.java)
                    startActivity(intent)
                }
                else -> {
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                    Toast.makeText(applicationContext, "else", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.fragment_home
=======
>>>>>>> master
    }

}

