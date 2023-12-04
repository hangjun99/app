package com.example.booksale

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException

class ChatlistActivity : AppCompatActivity() {
    lateinit var sendButton: Button
    lateinit var messageEditText: EditText
    lateinit var nickname: String
    lateinit var chatroom: String

    private lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        sendButton = findViewById<Button>(R.id.sendButton)
        messageEditText = findViewById<EditText>(R.id.messageEditText)
        //nickname chatroom 입력하세요
        //nickname = ~~~~~
        //chatroom = ~~~~~
        nickname = "dy"
        chatroom = "1-2"

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
            messageEditText.setText(message)
            // 메시지 수신 시 하고 창에 받은 메세지 보여주는 코드 추가해주세요
        }

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString()
            socket.emit("new_message", message, chatroom)
            messageEditText.text.clear()
            //자신의 nickname과 message로 보낸 메세지 보여주는 코드 추가해주세요
        }


    }
    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.emit("left", chatroom)
    }
}