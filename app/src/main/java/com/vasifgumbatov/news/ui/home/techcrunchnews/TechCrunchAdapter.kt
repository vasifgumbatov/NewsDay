package com.vasifgumbatov.news.ui.home.techcrunchnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.data.remote.response.Article
import com.vasifgumbatov.news.databinding.ItemChildNewsBinding

class TechCrunchAdapter : ListAdapter<Article, TechCrunchAdapter.TechViewHolder>(diffUtil) {

    private var onFavoriteClick: ((id: Int) -> Unit)? = null
    private var onItemClick: ((article: Article) -> Unit)? = null

    fun setOnFavoriteClick(listener: (id: Int) -> Unit) {
        this.onFavoriteClick = listener
    }


    fun setOnItemClick(listener: (article: Article) -> Unit) {
        this.onItemClick = listener
    }


    inner class TechViewHolder(private val binding: ItemChildNewsBinding) :
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
                onFavoriteClick?.invoke(position)
            }

            binding.root.setOnClickListener {
                onItemClick?.invoke(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechViewHolder {
        val binding = ItemChildNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TechViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TechViewHolder, position: Int) {
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