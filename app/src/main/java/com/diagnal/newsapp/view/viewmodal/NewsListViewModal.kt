package com.diagnal.newsapp.view.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diagnal.newsapp.modal.NewsDTO
import com.diagnal.newsapp.repositry.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListViewModal(private var repository: NewsRepo): ViewModel() {
     init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchNews()
        }
    }
    val articleList: LiveData<NewsDTO>? get() = repository.articleList
}