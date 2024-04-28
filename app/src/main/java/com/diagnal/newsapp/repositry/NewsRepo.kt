package com.diagnal.newsapp.repositry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.diagnal.newsapp.modal.NewsDTO
import com.diagnal.newsapp.network.ApiService


class NewsRepo(private var apiService: ApiService) {
    private val _newsList = MutableLiveData<NewsDTO>()
    val articleList: LiveData<NewsDTO>? get() = _newsList
    suspend fun fetchNews()  {
        try {
            val response = apiService.getNews()
            if (response.body() != null){
                _newsList.postValue(response.body())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }
}


