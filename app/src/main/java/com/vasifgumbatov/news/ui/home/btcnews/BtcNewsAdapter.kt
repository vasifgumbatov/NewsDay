package com.vasifgumbatov.news.ui.home.btcnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.data.remote.response.Article
import com.vasifgumbatov.news.databinding.ItemChildNewsBinding
import com.vasifgumbatov.news.databinding.ItemNewsHomeBinding

class BtcNewsAdapter : ListAdapter<Article, BtcNewsAdapter.BtcViewHolder>(diffUtil) {

    private var onItemClick: ((id: Int) -> Unit)? = null

    fun setOnItemClick(listener: (id: Int) -> Unit) {
        this.onItemClick = listener
    }

    inner class BtcViewHolder(private val binding: ItemChildNewsBinding) :
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

            binding.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("author", article.author)
                    putString("title", article.title)
                    putString("imageUrl", article.urlToImage)
                    putString("description", article.description)
                    putString("url", article.url)
                    putString("content", article.content)
                    putString("publishedAt", article.publishedAt)
                }

                Navigation.findNavController(it).navigate(R.id.action_btcNewsFragment_to_btcDetail2, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BtcViewHolder {
        val binding = ItemChildNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BtcViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BtcViewHolder, position: Int) {
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