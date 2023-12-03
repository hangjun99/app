package com.example.booksale

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray
import org.json.JSONException

class LoginActivity : AppCompatActivity(){

    var items = ArrayList<ClipData.Item>()
    var adapter: ItemAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage_activity)

        // 등록된 상품을 홈 화면에 보여주기
        var recyclerView:RecyclerView= findViewById(R.id.list)
        //adapter = ItemAdapter(this, items)
        recyclerView.setAdapter(adapter)

        //리사이클러뷰의 레이아웃 매니저 설정
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                    Toast.makeText(applicationContext, "home", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, LoginActivity::class.java)
                    startActivity(intent)

                }
                R.id.fragment_booksale -> {
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    Toast.makeText(applicationContext, "booksale", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, BookInsertActivity::class.java)
                    startActivity(intent)

                }
                R.id.fragment_search -> {
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    Toast.makeText(applicationContext, "검색", Toast.LENGTH_SHORT).show()
                    val intent= Intent(this@LoginActivity, SearchActivity::class.java)
                    startActivity(intent)

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
    }
    fun clickLoad(view: View?) {

        //서버의 php파일에 접속하여 (DB데이터들)결과 받기
        //Volley+ 라이브러리 사용

        //서버주소
        val serverUrl = "http://13.209.64.52/book_insert.php"

        //결과를 JsonArray 받을 것이므로..
        //StringRequest가 아니라..
        //JsonArrayRequest를 이용할 것임
        val jsonArrayRequest =
            JsonArrayRequest(Request.Method.POST, serverUrl, null, object :
                Response.Listener<JSONArray?> {
                //volley 라이브러리의 GET방식은 버튼 누를때마다 새로운 갱신 데이터를 불러들이지 않음. 그래서 POST 방식 사용
                fun onResponse(response: JSONArray) {
                    Toast.makeText(this@LoginActivity, response.toString(), Toast.LENGTH_SHORT)
                        .show()


                    //파라미터로 응답받은 결과 JsonArray를 분석
                    items.clear()
                    adapter?.notifyDataSetChanged()
                    try {
                        for (i in 0 until response.length()) {
                            val jsonObject = response.getJSONObject(i)
                            val BookName = jsonObject.getString("BookName")
                            val Edit= jsonObject.getString("Edit")
                            val Author = jsonObject.getString("Author")
                            var Publisher= jsonObject.getString("Publisher")
                            val HopePrice = jsonObject.getString("HopePrice")

                            //items.add(
                                ///* index = */ 0,
                                /* element = */ //ClipData.Item(BookName, Edit, Author, Publisher, HopePrice) ) // 첫 번째 매개변수는 몇번째에 추가 될지, 제일 위에 오도록
                            adapter?.notifyItemInserted(0)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onResponse(response: JSONArray?) {
                    TODO("Not yet implemented")
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Toast.makeText(this@LoginActivity, "ERROR", Toast.LENGTH_SHORT).show()
                }
            })

        //실제 요청 작업을 수행해주는 요청큐 객체 생성
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)

        //요청큐에 요청 객체 생성
        requestQueue.add(jsonArrayRequest)
    }



}