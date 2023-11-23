package com.example.booksale

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class SignupRequestActivity(
    ID:String, Password:String, UserName:String, NickName:String, PhoneNum: String,
    listener: Response.Listener<String?>)
    : StringRequest(Method.POST, URL, listener, null){
    private val map: MutableMap<String, String>

    init{
        map = HashMap()
        map["ID"] = ID
        map["Password"] = Password
        map["UserName"] = UserName
        map["NickName"] = NickName
        map["PhoneNum"] = PhoneNum
    }
    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String?>{
        return map
    }
    companion object{
        private const val URL ="http://52.78.52.80/signup_test.php"
    }
}