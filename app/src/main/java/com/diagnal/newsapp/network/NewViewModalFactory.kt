package com.diagnal.newsapp.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diagnal.newsapp.repositry.NewsRepo
import com.diagnal.newsapp.view.viewmodal.NewsListViewModal

class NewViewModalFactory(private val repo: NewsRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsListViewModal(repo) as T
    }
}