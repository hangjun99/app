package com.example.booksale

import android.content.Context
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class MsgRequestActivity (ChatRoom: String, Sender:String, Receiver: String, Msg:String,
                          listener: Response.Listener<String?>) : StringRequest(Method.POST, URL, listener, null){
    private val applicationContext: Context? = null
    private val map: MutableMap<String, String>

    init{
        map = HashMap()
        map["ChatRoom"] = ChatRoom
        map["Sender"] = Sender
        map["Receiver"] = Receiver
        map["Msg"] = Msg

    }
    val listener = Response.Listener<String?> { response ->
        Toast.makeText(applicationContext, "데이터 전송이 완료되었습니다.", Toast.LENGTH_SHORT).show()
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String?>{
        return map
    }
    companion object{
        private const val URL ="http://13.209.64.52/msg_insert.php"
    }
}