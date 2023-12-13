package com.example.booksale

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatlistActivity : AppCompatActivity() {
    lateinit var sendButton: Button
    lateinit var messageEditText: EditText
    lateinit var userInd: String
    lateinit var nickname: String
    lateinit var chatroom: String
    lateinit var opponentNickname: String

    private lateinit var socket: Socket
    private lateinit var chatAdapter: ChatAdapter
    private val chatList: MutableList<ChatMessage> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        sendButton = findViewById<Button>(R.id.sendButton)
        messageEditText = findViewById<EditText>(R.id.messageEditText)
        //nickname chatroom 입력하세요
        //nickname = ~~~~~
        //chatroom = ~~~~~
        userInd = intent.getStringExtra("UserInd").toString()
        nickname = intent.getStringExtra("Nickname").toString()
        chatroom = intent.getStringExtra("chatRoom").toString()
        opponentNickname = intent.getStringExtra("opponentNickname").toString()

        val recyclerView: RecyclerView = findViewById(R.id.chatRecyclerView)
        chatAdapter = ChatAdapter(chatList)
        recyclerView.adapter = chatAdapter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        try {
            socket = IO.socket("http://13.209.64.52:3000")
            socket.connect()
            Log.d("Connected", "OK")
        } catch (e: URISyntaxException) {
            Log.d("ERR", e.toString())
        }

        socket.on(Socket.EVENT_CONNECT) {
            socket.emit("enter_room", chatroom)
        }

        socket.on("new_message") { msg ->
            val message = msg[0].toString()
            // 메시지 수신 시 하고 창에 받은 메세지 보여주는 코드 추가해주세요
            displayReceivedMessage(opponentNickname, message)
        }

        sendButton.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "$nickname, $opponentNickname, $chatroom, $userInd",
                Toast.LENGTH_SHORT
            ).show()
            val message = messageEditText.text.toString()
            socket.emit("new_message", message, chatroom)
            messageEditText.text.clear()
            //자신의 nickname과 message로 보낸 메세지 보여주는 코드 추가해주세요

            // 자신이 보낸 메세지를 UI에 표시
            displaySentMessage(nickname, message)

            // 메시지 입력란 초기화
            messageEditText.text.clear()

            val responseListener: Response.Listener<String?> =
                Response.Listener<String?> { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        if (success) {
                            Toast.makeText(applicationContext, "메시지 전송", Toast.LENGTH_SHORT).show()
                            Log.d("ChatlistActivity", "메시지 전송성공")
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
            val msgRequestActivity = MsgRequestActivity(chatroom, nickname, opponentNickname, message, responseListener)
            val queue = Volley.newRequestQueue(applicationContext)
            queue.add(msgRequestActivity)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.emit("left", chatroom)
    }

    // UI에 받은 메시지를 표시하는 함수
    private fun displayReceivedMessage(opponentNickname: String, message: String) {
        // 받은 메시지를 화면에 표시하는 코드를 추가
        val formattedMessage = "$opponentNickname: $message"
        runOnUiThread {
            chatAdapter.addMessage(ChatMessage(opponentNickname, message))
        }
    }
    // UI에 자신이 보낸 메시지를 표시하는 함수
    private fun displaySentMessage(Nickname: String, message: String) {
        // 자신이 보낸 메시지를 화면에 표시하는 코드를 추가
        val sentMessage = "$Nickname: $message"
        Log.d("ChatlistActivity", "Displaying Received Message: $message")
        runOnUiThread {
            chatAdapter.addMessage(ChatMessage(Nickname, message))
        }
    }

    /*private fun loadChatHistoryFromDatabase() {
        val chatHistoryRequest = ChatHistory(
            ChatRoom = chatroom,
            listener = Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)

                    if (jsonObject.has("success") && jsonObject.getBoolean("success")) {
                        val sender = jsonObject.getString("Sender")
                        val msg = jsonObject.getString("Msg")

                        // UI에 메시지 표시
                        displayReceivedMessage("$sender: $msg")
                    } else {
                        // 채팅 기록이 없거나 오류 발생
                        Log.e("LoadChatHistory", "Failed to load chat history")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            // ... (기존 코드)
        )
        val queue = Volley.newRequestQueue(applicationContext)
        queue.add(chatHistoryRequest)
    }*/

    private fun toDate(currentMillis: Long): String {
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return formatter.format(Date(currentMillis))
    }

    private fun scrollToBottom() {
        val recyclerView = findViewById<RecyclerView>(R.id.chatRecyclerView)
        recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
    }
}