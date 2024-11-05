package com.vasifgumbatov.news.view.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.data.remote.response.Article
import com.vasifgumbatov.news.databinding.ItemNewsCardBinding

class NewsAdapter(private val newsList: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var onItemClick: ((id: Article) -> Unit)? = null

    fun setOnItemClick(listener: (id: Article) -> Unit) {
        this.onItemClick = listener
    }

    inner class NewsViewHolder(private val binding: ItemNewsCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.newsTitle.text = article.title
            binding.newsDescription.text = article.description
            binding.publishedTime.text = article.publishedAt
            Glide.with(binding.newsImage.context).load(article.urlToImage).into(binding.newsImage)
            binding.favoriteIC.setOnClickListener {
                onItemClick?.invoke(article)
                if (article.isLiked) {
                    binding.favoriteIC.setImageResource(R.drawable.favorite__state_active)
                } else {
                    binding.favoriteIC.setImageResource(R.drawable.ic_favorite)
                }
            }
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