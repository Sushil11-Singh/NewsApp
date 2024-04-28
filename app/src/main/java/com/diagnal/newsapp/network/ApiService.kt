package com.diagnal.newsapp.network

import com.diagnal.newsapp.modal.NewsDTO
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {
    @GET("news-api-feed/staticResponse.json")
   suspend fun getNews(): retrofit2.Response<NewsDTO> // Define your model class for Post
}