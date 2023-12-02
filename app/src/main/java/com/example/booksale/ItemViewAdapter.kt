package com.example.booksale

import android.os.AsyncTask
import android.os.Parcel
import android.os.Parcelable
import android.widget.ListAdapter
import android.widget.SimpleAdapter
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import androidx.recyclerview.widget.RecyclerView.Adapter as Adapter1

class ItemViewAdapter() : Adapter1 {
    var myJSON: String? = null
    var peoples: JSONArray? = null
    var personList: ArrayList<HashMap<String, String?>>? = null
    var list: RecyclerView? = null

    list = findViewById<RecyclerView>(R.id.list)
    personList = ArrayList()
    getData("http://52.78.52.80/.php")




    protected fun showList() {
        try {
            val jsonObj = JSONObject(myJSON)
            peoples = jsonObj.getJSONArray(TAG_RESULTS)
            for (i in 0 until peoples.length()) {
                val c = peoples.getJSONObject(i)
                val id = c.getString(TAG_ID)
                val name = c.getString(TAG_NAME)
                val address = c.getString(TAG_ADD)
                val persons = HashMap<String, String?>()
                persons[TAG_ID] = id
                persons[TAG_NAME] = name
                persons[TAG_ADD] = address
                personList!!.add(persons)
            }
            val adapter: ListAdapter = SimpleAdapter(
                this@LoginActivity,
                personList,
                R.layout.item_article,
                arrayOf<String>(TAG_ID, TAG_NAME, TAG_ADD),
                intArrayOf(android.R.id.id, android.R.id.name, android.R.id.address)
            )
            list!!.adapter = adapter
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    private fun getData(url: String?) {
        abstract class GetDataJSON :
            AsyncTask<String?, Void?, String?>() {
            protected override fun doInBackground(vararg params: String?): String? {
                val uri = params[0]
                var bufferedReader: BufferedReader? = null
                return try {
                    val url = URL(uri)
                    val con = url.openConnection() as HttpURLConnection
                    val sb = StringBuilder()
                    bufferedReader = BufferedReader(InputStreamReader(con.inputStream))
                    var json: String
                    while (bufferedReader.readLine().also { json = it } != null) {
                        sb.append(
                            """
                                $json
                                
                                """.trimIndent()
                        )
                    }
                    sb.toString().trim { it <= ' ' }
                } catch (e: Exception) {
                    null
                }
            }

            override fun onPostExecute(result: String?) {
                myJSON = result
                showList()
            }
        }

        val g = GetDataJSON()
        g.execute(url)
    }
    companion object {
        private const val TAG_RESULTS = "result"
        private const val TAG_ID = "id"
        private const val TAG_NAME = "name"
        private const val TAG_ADD = "address"
    }
}