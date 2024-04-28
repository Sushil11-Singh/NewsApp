package com.diagnal.newsapp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.diagnal.newsapp.R
import com.diagnal.newsapp.modal.Article
import com.diagnal.newsapp.view.ui.NewsDetailActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL


class NewsListAdapter (private val context: Context, private val lifecycleScope: LifecycleCoroutineScope
) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>(){
    private var articleList = mutableListOf<Article>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listing_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articleList[position]
        binDataToTheView(article,holder)
    }

    private fun binDataToTheView(article: Article, holder: ViewHolder) {
        article.title.let {
            holder.tvNewsTitle.text = it
        }
        article.description.let {
            holder.tvNewsDesc.text = it
        }
        article.publishedAt.let {
            val pubDate = it?.split("T")
            holder.tvNewsDate.text = pubDate?.get(0) ?: "NA"
        }
        article.source.let {
            holder.tvNewsSource.text = it?.name
        }
        val requestOptions: RequestOptions = RequestOptions()
            .placeholder(R.drawable.placeholder_view_vector_svg) // Placeholder image while loading
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache both original & resized image

        article.urlToImage.let {
            Glide.with(context)
                .load(it)
                .apply(requestOptions)
                .into(holder.ivNews)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("loadUrl",article.url)
            context.startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return articleList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(newContentList: List<Article>) {
        articleList.addAll(newContentList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNewsTitle: TextView = itemView.findViewById(R.id.tvNewsTitle)
        val tvNewsDesc: TextView = itemView.findViewById(R.id.tvNewsDesc)
        val tvNewsDate: TextView = itemView.findViewById(R.id.tvNewsDate)
        val tvNewsSource: TextView = itemView.findViewById(R.id.tvNewsSource)
        val ivNews: ImageView = itemView.findViewById(R.id.ivNews)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    private suspend fun downloadBitmap(imageUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val con = URL(imageUrl).openConnection()
                con.connect()
                val inputStream = con.getInputStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
                bitmap
            } catch (ex: Exception) {
                Log.e("DownloadBitmap", "Exception: $ex")
                null
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sortArticles(sortingOption: String) {
        when (sortingOption) {
            "old_to_new" -> {
                articleList.sortBy { it.publishedAt }
            }
            "new_to_old" -> {
                articleList.sortByDescending { it.publishedAt }
            }
            else -> {
                // Default sorting (old_to_new)
                articleList.sortBy { it.publishedAt }
            }
        }
        notifyDataSetChanged()
    }

}