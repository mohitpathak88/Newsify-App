package com.example.news

data class News (
    val title: String,
    val author: String,
    val url: String,
    val imageUrl: String
)
//Fields that we have to keep in our array
//we have to pass the array of News into our adapter