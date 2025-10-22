package com.example.newsfresh

import android.net.Uri // Use the correct Uri import
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent // Cleaner import
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), NewsItemClicked {

    // Keep the adapter private as a good practice
    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // 1. Set the layout manager for the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 2. Initialize the adapter ONCE and set it on the RecyclerView.
        //    The adapter will be empty at first, which is correct.
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter

        // 3. Fetch the data only ONCE.
        fetchData()
    }

    private fun fetchData() {
        val url =
            "https://newsapi.org/v2/everything?q=tesla&from=2023-09-16&sortBy=publishedAt&apiKey=53a000c040194dcabd9c986ba3e90802"


        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val newsJsonArray = response.getJSONArray("articles")
            val newsArray = ArrayList<News>()
            for (i in 0 until newsJsonArray.length()) {
                val newsJsonObject = newsJsonArray.getJSONObject(i)
                val news = News(
                    newsJsonObject.getString("title"),
                    newsJsonObject.getString("author"),
                    newsJsonObject.getString("url"),
                    newsJsonObject.getString("urlToImage")
                )
                newsArray.add(news)
            }
            // 4. THE FIX: Call an 'updateNews' method on the EXISTING adapter.
            //    DO NOT create a new adapter here.
            mAdapter.updateNews(newsArray)
        }, { error ->
            Toast.makeText(this, "API Error: ${error.message}", Toast.LENGTH_LONG).show()
        })

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {


        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));

    }
}
