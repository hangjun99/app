package com.example.booksale

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONException
import org.json.JSONObject

class SearchActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage_activity)

        val UserInd = intent.getIntExtra("UserInd",0)
        val NickName = intent.getStringExtra("Nickname")
        val Search = intent.getStringExtra("search")
        lateinit var chatRoom: String

        // 등록된 상품을 홈 화면에 보여주기
        recyclerView = findViewById(R.id.list)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val responseListener: Response.Listener<String?> =
            Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")

                    if (success) {
                        Toast.makeText(
                            applicationContext,
                            "책 정보 가져오기 성공",
                            Toast.LENGTH_SHORT
                        ).show()
                        val adapter = ItemActivity()
                        val jsonArray = jsonObject.getJSONArray("books")
                        for(i in 0 until jsonArray.length()){
                            val data = jsonArray.getJSONObject(i)
                            val BookInd = data.getInt("BookInd")
                            val UploaderInd = data.getInt("UploaderInd")
                            val Uploader = data.getString("Uploader")
                            val BookName = data.getString("BookName")
                            val Edit = data.getInt("Edit")
                            val Author = data.getString("Author")
                            val Publisher = data.getString("Publisher")
                            val HopePrice = data.getInt("HopePrice")
                            val Description = data.getString("Description")

                            if(UserInd < UploaderInd){
                                val items = listOf(UserInd.toString(), UploaderInd.toString())
                                chatRoom = items.joinToString("-")
                            } else if(UserInd > UploaderInd){
                                val items = listOf(UploaderInd.toString(), UserInd.toString())
                                chatRoom = items.joinToString("-")
                            } else{
                                chatRoom = "자신이 올린 물건을 살 순 없습니다."
                            }
                            val detailData = arrayOf<String>(BookInd.toString(), UploaderInd.toString(), Uploader, BookName, Edit.toString(), Author, Publisher, HopePrice.toString(), Description, chatRoom, UserInd.toString(), NickName.toString())
                            adapter.addItem(ItemActivity.Product(BookName,Author,Publisher,HopePrice.toString(), detailData))
                        }
                        recyclerView.adapter = adapter
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "책 정보 가져오기 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "예외 1", Toast.LENGTH_SHORT).show()
                    return@Listener
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        val BookSearchRequestActivity =
            BookSearchRequestActivity(Search.toString(), responseListener)
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
        queue.add(BookSearchRequestActivity)


        /*adapter.setOnItemClickListener(object: ItemActivity.OnProductItemClicklistener(){
            override fun onItemClick(holder: ItemActivity.ViewHolder?, view: View?, position:Int){
                val item: ItemActivity.Product = adapter.getItem(position)
                Toast.makeText(applicationContext,
                    """${
                        """책이름 : ${item.bookName}
                            저자 : ${item.author}
                            출판사 : ${item.publisher}"""}
                            가격 : ${item.hopePrice}""", Toast.LENGTH_LONG
                ).show()
            }
        })*/

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv1)
                    Toast.makeText(applicationContext, "home", Toast.LENGTH_SHORT).show()
                    val responseListener: Response.Listener<String?> =
                        Response.Listener<String?> { response ->
                            try {
                                val jsonObject = JSONObject(response)
                                val success = jsonObject.getBoolean("success")

                                if (success) {
                                    Toast.makeText(
                                        applicationContext,
                                        "책 정보 가져오기 성공",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val adapter = ItemActivity()
                                    val jsonArray = jsonObject.getJSONArray("books")
                                    for(i in 0 until jsonArray.length()){
                                        val data = jsonArray.getJSONObject(i)
                                        val BookInd = data.getInt("BookInd")
                                        val UploaderInd = data.getInt("UploaderInd")
                                        val Uploader = data.getString("Uploader")
                                        val BookName = data.getString("BookName")
                                        val Edit = data.getInt("Edit")
                                        val Author = data.getString("Author")
                                        val Publisher = data.getString("Publisher")
                                        val HopePrice = data.getInt("HopePrice")
                                        val Description = data.getString("Description")

                                        if(UserInd < UploaderInd){
                                            val items = listOf(UserInd.toString(), UploaderInd.toString())
                                            chatRoom = items.joinToString("-")
                                        } else if(UserInd > UploaderInd){
                                            val items = listOf(UploaderInd.toString(), UserInd.toString())
                                            chatRoom = items.joinToString("-")
                                        } else{
                                            chatRoom = "자신이 올린 물건을 살 순 없습니다."
                                        }
                                        val detailData = arrayOf<String>(BookInd.toString(), UploaderInd.toString(), Uploader, BookName, Edit.toString(), Author, Publisher, HopePrice.toString(), Description, chatRoom, UserInd.toString(), NickName.toString())
                                        adapter.addItem(ItemActivity.Product(BookName,Author,Publisher,HopePrice.toString(), detailData))
                                    }
                                    recyclerView.adapter = adapter
                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        "책 정보 가져오기 실패",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return@Listener
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                Toast.makeText(applicationContext, "예외 1", Toast.LENGTH_SHORT).show()
                                return@Listener
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                    val bookRequestActivity =
                        BookRequestActivity(NickName.toString(), responseListener)
                    val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
                    queue.add(bookRequestActivity)
                    // Respond to navigation item 1 click
                }
                R.id.fragment_booksale -> {
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    Toast.makeText(applicationContext, "booksale", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, BookInsertActivity::class.java)
                    intent.putExtra("UserInd", UserInd)
                    intent.putExtra("NickName", NickName)
                    startActivity(intent)
                    // Respond to navigation item 2 click
                }
                R.id.fragment_search -> {
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    Toast.makeText(applicationContext, "검색", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SearchBookActivity::class.java)
                    intent.putExtra("UserInd", UserInd)
                    intent.putExtra("Nickname", NickName)
                    startActivity(intent)
                    // Respond to navigation item 3 click
                }
                R.id.fragment_chatList->{
                    bottomNavigation.itemIconTintList = ContextCompat.getColorStateList(this, R.color.color_bnv2)
                    bottomNavigation.itemTextColor = ContextCompat.getColorStateList(this, R.color.color_bnv2)

                    val intent = Intent(this, ChatRoomActivity::class.java)
                    intent.putExtra("UserInd", UserInd)
                    intent.putExtra("Nickname", NickName)
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



}