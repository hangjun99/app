package com.example.booksale

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ItemViewAdapter : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_article)

        val titleView = findViewById<TextView>(R.id.titleTextView)
        val writerView = findViewById<TextView>(R.id.writerText)
        val publisherView = findViewById<TextView>(R.id.publisherText)
        val priceView = findViewById<TextView>(R.id.priceTextView)
        val Button = findViewById<Button>(R.id.chatBtn)

        val BookName = titleView.text.toString()
        val Author = writerView.text.toString()
        val Publisher = publisherView.text.toString()
        val HopePrice =
            priceView.text.toString()

        val responseListener =
            Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")
                    if (success) {
                        val msg = jsonObject.getString("")
                        Toast.makeText(
                            applicationContext,
                            " :$msg",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "예외 1", Toast.LENGTH_SHORT).show()
                    return@Listener
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        val BookInsertRequest = BookInsertRequest("1",BookName, "1",Author, Publisher, HopePrice,
            "",responseListener)
        val queue = Volley.newRequestQueue(applicationContext)
        queue.add(BookInsertRequest)
    }

}