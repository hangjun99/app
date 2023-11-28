package com.example.booksale

import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class IdCheckActivity (ID:String,  listener: Response.Listener<String?>)
    : StringRequest(Method.POST, URL, listener, null) {
    private val map: MutableMap<String, String>

    init{
        map = HashMap()
        map["ID"] = ID
    }
    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String?>{
        return map
    }
    companion object{
        private const val URL ="http://52.78.52.80/ID_check.php"
    }

}