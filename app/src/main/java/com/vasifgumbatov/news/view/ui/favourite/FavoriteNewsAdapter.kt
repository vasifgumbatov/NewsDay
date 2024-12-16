package com.vasifgumbatov.news.view.ui.favourite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.databinding.ItemFavoriteBinding
import com.vasifgumbatov.news.view.ui.detail.FavoriteDetailActivity

class FavoriteNewsAdapter(private var favoriteList: List<FavoriteEntity>) :
    RecyclerView.Adapter<FavoriteNewsAdapter.FavoriteNewsViewHolder>() {

    private var onItemClick: ((id: Int) -> Unit)? = null

    fun setOnItemClick(listener: (id: Int) -> Unit) {
        this.onItemClick = listener
    }

    inner class FavoriteNewsViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteEntity, position: Int) {
            with(binding) {
                newsAuthor.text = favorite.author
                newsTitle.text = favorite.title
                newsUrl.text = favorite.url
                publishedTime.text = favorite.publishedAt
                Glide.with(newsImage.context).load(favorite.urlToImage).into(newsImage)
            }

            binding.deleteIC.setOnClickListener {
                onItemClick?.invoke(position)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, FavoriteDetailActivity::class.java)
                with(intent) {
                    putExtra("author", favorite.author)
                    putExtra("title", favorite.title)
                    putExtra("imageUrl", favorite.urlToImage)
                    putExtra("description", favorite.description)
                    putExtra("url", favorite.url)
                    putExtra("publishedAt", favorite.publishedAt)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteNewsViewHolder {
        val binding =
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteNewsViewHolder, position: Int) {
        holder.bind(favoriteList[position], position)
    }

    override fun getItemCount(): Int = favoriteList.size
}

