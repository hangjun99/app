package com.example.booksale

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class DetailBookDataActivity: AppCompatActivity() {
    lateinit var bookname: TextView
    lateinit var edit: TextView
    lateinit var author: TextView
    lateinit var publisher: TextView
    lateinit var price_edit: TextView
    lateinit var explan: TextView
    lateinit var uploader: TextView
    lateinit var submitButton: Button

    lateinit var chatRoom: String
    lateinit var UserInd: String
    lateinit var Nickname: String
    lateinit var Uploader: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_book_data)

        bookname = findViewById<TextView>(R.id.titleEditText)
        edit = findViewById<TextView>(R.id.edit_Text)
        author = findViewById<TextView>(R.id.writerEditText)
        publisher = findViewById<TextView>(R.id.publisher)
        price_edit = findViewById<TextView>(R.id.price_edit)
        explan = findViewById<TextView>(R.id.explan)
        uploader = findViewById<TextView>(R.id.uploaderTV)
        submitButton = findViewById<Button>(R.id.submitButton)

        val detailBook = intent.getStringArrayExtra("detailBook")

        //서적정보 가져오기
        if (detailBook != null && detailBook.size == 12) {
            Uploader = detailBook[2]
            val BookName = detailBook[3]
            val Edit = detailBook[4]
            val Author = detailBook[5]
            val Publisher = detailBook[6]
            val HopePrice = detailBook[7]
            val Description = detailBook[8]
            chatRoom = detailBook[9]
            UserInd = detailBook[10]
            Nickname = detailBook[11]

            bookname.setText("책 이름: " + BookName)
            edit.setText("판(버전): " + Edit)
            author.setText("저자: " + Author)
            publisher.setText("출판사: " + Publisher)
            price_edit.setText("희망가격: " + HopePrice)
            explan.setText("상세설명: " + Description)
            uploader.setText("판매자는: " + Uploader)
            // 이후 변수들을 활용하여 필요한 작업을 수행
            if(chatRoom == "자신이 올린 물건을 살 순 없습니다."){
                Toast.makeText(
                    applicationContext,
                    "자신이 올린 물건을 살 순 없습니다.",
                    Toast.LENGTH_SHORT
                ).show()
                submitButton.isEnabled = false
            }
        }

        submitButton.setOnClickListener {
            val responseListener: Response.Listener<String?> =
                Response.Listener<String?> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success) {
                            Toast.makeText(
                                applicationContext,
                                "채팅룸 생성",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "채팅룸 존재",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Listener
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(applicationContext, "예외 2", Toast.LENGTH_SHORT).show()
                        return@Listener
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            val ChatRoomInsertRequestActivity =
                ChatRoomInsertRequestActivity(chatRoom, Uploader, Nickname, responseListener)
            val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
            queue.add(ChatRoomInsertRequestActivity)


            val intent = Intent(this, ChatlistActivity::class.java)
            intent.putExtra("chatRoom", chatRoom)
            intent.putExtra("UserInd", UserInd)
            intent.putExtra("Nickname", Nickname)
            intent.putExtra("opponentNickname", Uploader)
            startActivity(intent)
        }


    }
}