package com.example.booksale

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class SearchBookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_search)

        val UserInd = intent.getIntExtra("UserInd",0)
        val NickName = intent.getStringExtra("NickName")

        val searchView = findViewById<SearchView>(R.id.search)

        // SearchView의 아이콘 클릭 시 이벤트 처리
        searchView.setOnSearchClickListener {
            Toast.makeText(this, "검색창이 열렸습니다.", Toast.LENGTH_SHORT).show()
        }

        // SearchView의 검색 버튼 클릭 시 이벤트 처리
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 사용자가 검색어를 입력하고 제출했을 때 처리할 내용
                Toast.makeText(this@SearchBookActivity, "검색어: $query", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@SearchBookActivity, SearchActivity::class.java)
                intent.putExtra("UserInd", UserInd)
                intent.putExtra("Nickname", NickName)
                intent.putExtra("search", query)
                startActivity(intent)
                return true
            }



            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어가 변경될 때마다 호출되는 메서드
                // newText에 현재 입력된 검색어가 전달됩니다.
                return true
            }
        })
    }
}