package com.diagnal.newsapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diagnal.newsapp.R
import com.diagnal.newsapp.network.ApiService
import com.diagnal.newsapp.network.NewViewModalFactory
import com.diagnal.newsapp.network.RetrofitHelper
import com.diagnal.newsapp.repositry.NewsRepo
import com.diagnal.newsapp.utils.AppUtils
import com.diagnal.newsapp.view.adapter.NewsListAdapter
import com.diagnal.newsapp.view.viewmodal.NewsListViewModal
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var rvNews: RecyclerView
    private lateinit var ivFilter : ImageView
    private lateinit var newsListAdapter: NewsListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var newsListViewModal: NewsListViewModal
    private lateinit var progressBar: ProgressBar
    private lateinit var firebaseMessaging: FirebaseMessaging
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
        firebaseMessaging = FirebaseMessaging.getInstance()
        firebaseSetUp()
    }

    private fun firebaseSetUp() {
        firebaseMessaging.token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                Log.d("TAG", "Token: $token")
                // Send token to server
            } else {
                Log.w("TAG", "Failed to get token")

        }
    }
    }

    private fun initLayout() {
        rvNews = findViewById(R.id.rvNews)
        ivFilter = findViewById(R.id.ivFilter)
        progressBar = findViewById(R.id.progressBar)
        layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvNews.layoutManager = layoutManager
        rvNews.setHasFixedSize(true)
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val repository = NewsRepo(apiService)
        newsListViewModal = ViewModelProvider(this,NewViewModalFactory(repository)).get(NewsListViewModal::class.java)
        newsListAdapter = NewsListAdapter(this,lifecycleScope)
        rvNews.adapter = newsListAdapter
        fetchData()
        filterClick()
    }

    private fun filterClick() {
        ivFilter.setOnClickListener {
            AppUtils.showAlert(this) { sortingOption ->
                // Sort articles based on selected sorting option
                newsListAdapter.sortArticles(sortingOption)
            }
        }
    }

    private fun fetchData() {
        newsListViewModal.articleList?.observe(this, Observer { data ->
            progressBar.visibility = View.VISIBLE
            if (data != null) {
                newsListAdapter.addData(data.articles)
                progressBar.visibility = View.GONE
            }
        })
    }
}