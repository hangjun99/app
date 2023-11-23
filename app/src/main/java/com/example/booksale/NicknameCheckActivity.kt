package com.example.booksale

import android.provider.ContactsContract.CommonDataKinds.Website.URL
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class NicknameCheckActivity(NickName:String,  listener: Response.Listener<String?>)
    : StringRequest(Method.POST, URL, listener, null) {
    private val map: MutableMap<String, String>

    init{
        map = HashMap()
        map["NickName"] =NickName
    }
    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String?>{
        return map
    }
    companion object{
        private const val URL ="http://52.78.52.80/nickname_check.php"
    }

}