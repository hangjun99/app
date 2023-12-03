package com.example.booksale

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class SearchActivity : AppCompatActivity() {
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_search)

        searchView = findViewById(R.id.search)


        // Set up SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchMovie(query!!)
                // Handle the search query submission if needed
                return false
            }

            private fun searchMovie(query: String) {

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterBooks(newText)
                return true
            }
        })


    }
    private fun filterBooks(query: String?) {
        val url = "http://13.209.64.52/book_insert.php?searchQuery=$query"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Handle the JSON response here
                // Parse the JSON response and update your RecyclerView accordingly
            },
            { error ->
                // Handle errors
                Log.e("SearchActivity", "Error: $error")
            })

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request)
    }

}