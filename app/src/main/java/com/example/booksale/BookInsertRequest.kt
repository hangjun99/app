package com.example.booksale

import ItemViewAdapter
import android.content.Context
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class BookInsertRequest(
    UserInd:Int, BookName:String, Edit:Int, Author:String, Publisher:String, HopePrice:Int, Description:String, PhoneNum: String,
                         listener: Response.Listener<String?>)
    :StringRequest(Method.POST, URL, listener, null){
    private val applicationContext: Context? = null
    private val map: MutableMap<String, String>

    init{
        map = HashMap()
        map["UserInd"] = UserInd.toString()
        map["BookName"] = BookName
        map["Edit"] = Edit.toString()
        map["Author"] = Author
        map["Publisher"] = Publisher
        map["HopePrice"] = HopePrice.toString()
        map["Description"] = Description
    }
    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String?>{
        return map
    }
    companion object{
        private const val URL ="http://13.209.64.52/book_insert.php"
    }


}