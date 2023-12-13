package com.example.booksale

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class BookInsertActivity: AppCompatActivity() {
    lateinit var bookname: EditText
    lateinit var edit: EditText
    lateinit var author: EditText
    lateinit var publisher: EditText
    lateinit var price_edit: EditText
    lateinit var explan: EditText
    lateinit var submitButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_booksale)

        bookname = findViewById<EditText>(R.id.titleEditText)
        edit = findViewById<EditText>(R.id.edit_Text)
        author = findViewById<EditText>(R.id.writerEditText)
        publisher = findViewById<EditText>(R.id.publisher)
        price_edit = findViewById<EditText>(R.id.price_edit)
        explan = findViewById<EditText>(R.id.explan)
        submitButton = findViewById<Button>(R.id.submitButton)
        val UserInd = intent.getIntExtra("UserInd",0)
        val NickName = intent.getStringExtra("NickName").toString()

        //서적등록
        submitButton.setOnClickListener{
            val BookName = bookname.text.toString()
            val Edit = edit.text.toString()
            Log.d("BookInsertActivity", "Edit: $Edit")
            val Author = author.text.toString()
            val Publisher = publisher.text.toString()
            val HopePrice = price_edit.text.toString()
            val Description = explan.text.toString()

            val responseListener: Response.Listener<String?> = Response.Listener<String?>{ response ->
                try{
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if(success){
                        Toast.makeText(applicationContext,"판매상품이 등록되었습니다..", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(applicationContext, "판매상품 등록 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        return@Listener
                    }
                } catch (e: JSONException){
                    e.printStackTrace()
                    Toast.makeText(applicationContext,"예외 1", Toast.LENGTH_SHORT).show()
                    return@Listener
                }
            }
            val bookInsertActivity = BookInsertRequest(UserInd.toString(), NickName, BookName, Edit, Author, Publisher, HopePrice, Description, responseListener)
            val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
            queue.add(bookInsertActivity)
        }


    }

}