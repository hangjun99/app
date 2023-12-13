package com.example.booksale

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ChatRoomActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_chat_list)

        val UserInd = intent.getIntExtra("UserInd",0)
        val Nickname = intent.getStringExtra("Nickname")
        lateinit var chatRoom: String
        lateinit var opponentNickname: String
        lateinit var lastMsg: String

        recyclerView = findViewById(R.id.chatListRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val responseListener: Response.Listener<String?> =
            Response.Listener<String?> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")

                    if (success) {
                        Toast.makeText(
                            applicationContext,
                            "room 정보 가져오기 성공",
                            Toast.LENGTH_SHORT
                        ).show()
                        val adapter = ChatItemActivity()//나중에
                        val jsonArray = jsonObject.getJSONArray("rooms")
                        for(i in 0 until jsonArray.length()){
                            val data = jsonArray.getJSONObject(i)
                            chatRoom = data.getString("ChatRoom")
                            val seller = data.getString("Seller")
                            val buyer = data.getString("Buyer")
                            val createAt = data.getString("CreateAt")

                            if(Nickname == seller){
                                opponentNickname = buyer
                            }else{
                                opponentNickname = seller
                            }

                            lastMsg = getLastMsg(chatRoom, applicationContext)
                            Toast.makeText(applicationContext, "ㅁㅁ$lastMsg 성공", Toast.LENGTH_SHORT).show()


                            val detailData = arrayOf<String>(chatRoom, seller, buyer, createAt, UserInd.toString(), Nickname.toString(), opponentNickname)
                            adapter.addItem(ChatItemActivity.Product(opponentNickname, lastMsg, detailData))
                        }
                        recyclerView.adapter = adapter
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "room 정보 가져오기 실패",
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
        val ChatRoomRequestActivity =
            ChatRoomRequestActivity(Nickname.toString(), responseListener)
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
        queue.add(ChatRoomRequestActivity)
    }

}

fun getLastMsg(chatRoom: String, context: Context): String{
    var lastMsg = ""
    val responseListener1: Response.Listener<String?> =
        Response.Listener<String?> { response ->
            try {
                val jsonObject = JSONObject(response)
                val success = jsonObject.getBoolean("success")
                if (success) {
                    lastMsg = jsonObject.getString("Msg")
                    Toast.makeText(
                        context,
                        "last msg 가져오기 성공",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "last msg 가져오기 실패",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Listener
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Toast.makeText(context, "예외 2", Toast.LENGTH_SHORT).show()
                return@Listener
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    val LastMsgRequestActivity =
        LastMsgRequestActivity(chatRoom, responseListener1)
    val queue: RequestQueue = Volley.newRequestQueue(context)
    queue.add(LastMsgRequestActivity)
    return lastMsg
}