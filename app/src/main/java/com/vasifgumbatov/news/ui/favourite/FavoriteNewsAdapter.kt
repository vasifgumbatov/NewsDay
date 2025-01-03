package com.vasifgumbatov.news.ui.favourite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.databinding.ItemFavoriteBinding

class FavoriteNewsAdapter(private var favoriteList: MutableList<FavoriteEntity>) :
    RecyclerView.Adapter<FavoriteNewsAdapter.FavoriteNewsViewHolder>() {

    private var onDeleteClick: ((favorite: FavoriteEntity) -> Unit)? = null
    private var onItemClick: ((favorite: FavoriteEntity) -> Unit)? = null

    fun setOnDeleteClick(listener: (favorite: FavoriteEntity) -> Unit) {
        this.onDeleteClick = listener
    }


    fun setOnItemClick(listener: (favorite: FavoriteEntity) -> Unit) {
        this.onItemClick = listener
    }



    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<FavoriteEntity>) {
        favoriteList.clear()
        favoriteList.addAll(newList)
        notifyDataSetChanged()
    }

    fun removeItem(favorite: FavoriteEntity) {
        val position = favoriteList.indexOf(favorite)
        if (position != -1) {
            favoriteList.removeAt(position)
            notifyItemRemoved(position)
        }
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
                onDeleteClick?.invoke(favorite)
            }

            binding.root.setOnClickListener {
                onItemClick?.invoke(favorite)
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

