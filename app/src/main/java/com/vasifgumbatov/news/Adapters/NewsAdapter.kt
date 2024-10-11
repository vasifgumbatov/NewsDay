package com.vasifgumbatov.news.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.Response.Article
import com.vasifgumbatov.news.databinding.ItemNewsCardBinding

class NewsAdapter(private val newsList: List<Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

//    private var onItemClick: ((id: Int?) -> Unit)? = null
//    fun setOnItemClick(listener: (id: Int?) -> Unit){
//        this.onItemClick = listener
//    }

    inner class NewsViewHolder(private val binding: ItemNewsCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.newsTitle.text = article.title
            binding.newsDescription.text = article.description
            binding.publishedTime.text = article.publishedAt
            Glide.with(binding.newsImage.context).load(article.urlToImage).into(binding.newsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])

    }

    override fun getItemCount() = newsList.size
}

private fun <P1, R> ((P1) -> R)?.invoke(id: String): Unit {
    TODO("Not yet implemented")
}
