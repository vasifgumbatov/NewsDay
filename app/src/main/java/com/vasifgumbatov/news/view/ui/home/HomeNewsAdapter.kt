package com.vasifgumbatov.news.view.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.data.remote.response.Article
import com.vasifgumbatov.news.databinding.ItemNewsHomeBinding
import com.vasifgumbatov.news.view.ui.detail.HomeDetailActivity

class HomeNewsAdapter : ListAdapter<Article, HomeNewsAdapter.NewsViewHolder>(diffUtil) {

    private var onItemClick: ((id: Int) -> Unit)? = null

    fun setOnItemClick(listener: (id: Int) -> Unit) {
        this.onItemClick = listener
    }

    inner class NewsViewHolder(private val binding: ItemNewsHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, position: Int) {
            with(binding) {
                newsAuthor.text = article.author
                newsTitle.text = article.title
                newsUrl.text = article.url
                publishedTime.text = article.publishedAt
            }

            Glide.with(binding.newsImage.context).load(article.urlToImage).into(binding.newsImage)

            if (article.isLiked) {
                binding.favoriteIC.setImageResource(R.drawable.favorite__state_active)
            } else {
                binding.favoriteIC.setImageResource(R.drawable.ic_favorite)
            }

            binding.favoriteIC.setOnClickListener {
                onItemClick?.invoke(position)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, HomeDetailActivity::class.java)
                with(intent) {
                    putExtra("author", article.author)
                    putExtra("title", article.title)
                    putExtra("imageUrl", article.urlToImage)
                    putExtra("description", article.description)
                    putExtra("url", article.url)
                    putExtra("content", article.content)
                    putExtra("publishedAt", article.publishedAt)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding =
            ItemNewsHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.author == newItem.author
                        && oldItem.title == newItem.title
                        && oldItem.description == newItem.description
                        && oldItem.url == newItem.url
                        && oldItem.content == newItem.content
                        && oldItem.publishedAt == newItem.publishedAt
            }
        }
    }
}