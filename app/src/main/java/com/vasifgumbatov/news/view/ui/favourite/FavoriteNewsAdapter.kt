package com.vasifgumbatov.news.view.ui.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.databinding.ItemFavoriteBinding

class FavoriteNewsAdapter(private var favoriteList: List<FavoriteEntity>) :
    RecyclerView.Adapter<FavoriteNewsAdapter.FavoriteNewsViewHolder>() {

    private var favoriteNewsList = emptyList<FavoriteEntity>()
    private var onDeleteClick: ((id: FavoriteEntity) -> Unit)? = null

    inner class FavoriteNewsViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteEntity) {
            with(binding) {
                newsTitle.text = favorite.title
                newsDescription.text = favorite.description
                Glide.with(newsImage.context).load(favorite.urlToImage).into(newsImage)
                deleteIC.setOnClickListener {
                    onDeleteClick?.invoke(favorite)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteNewsViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteNewsViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = favoriteList.size
}
