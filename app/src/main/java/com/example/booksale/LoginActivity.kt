package com.example.booksale

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class LoginActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage_activity)

        // 등록된 상품을 홈 화면에 보여주기
        recyclerView = findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 하단 버튼 눌러 화면 전환
        val bottomNavigation:BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    val intent = Intent(this@LoginActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                R.id.fragment_booksale -> {

                }

                R.id.search -> {

                }

                R.id.fragment_chatList->{
                    val intent = Intent(this@LoginActivity, ChatlistActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.fragment_myPage ->{
                    val intent = Intent(this@LoginActivity, MainActivity::class.java )
                    startActivity(intent)
                    finish()
                }
            }
            true
        }
        bottomNavigation.selectedItemId = R.id.fragment_home //초기값 세팅
    }

}