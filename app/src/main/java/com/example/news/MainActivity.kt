package com.example.news

import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.memesshare.MySingleton
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter   //m means its a member variable which can be accessed from anywhere

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager =  LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        //Layout Manager tells which type of layout does it have (Linear,Grid,Staggered)
        //Indicates this is a linear layout
        fetchData()
        mAdapter = NewsListAdapter(this)       //Inserting data into adapter
        recyclerView.adapter = mAdapter          //Linking Adapter to the recyclerview
    }

    private fun fetchData(){
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=7bfaa34de20042f7a9caac210340c518"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                //Articles is the array of json objects. Here we are extracting the Json Array
                //Now Passing this into News list
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(     //Creating news object in which we pass all required things
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                    )
                    newsArray.add(news)  //Adding the news object in news array
                    //Now we have to pass this news array to adapter as 'Array List of news'
                }
                mAdapter.updateNews(newsArray)      //Passing newsarray into adapter
            },
            {
                Log.d("rishu", it.message?:"")
            }



        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
      //  Log.d("rishu", "item pressed $item")
      //  Toast.makeText(this, "clicked item is $item", Toast.LENGTH_SHORT).show()
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
        //These lines would help in opening the news link on custom chrome tab
    }
}