package com.vasifgumbatov.news.ui.home.btcnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vasifgumbatov.news.databinding.ItemLoadStateBinding

class NewsLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<NewsLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding, retry)
    }

    class LoadStateViewHolder(
        private val binding: ItemLoadStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.loadStateRetryButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            binding.loadStateProgressBar.isVisible = loadState is LoadState.Loading
            binding.loadStateRetryButton.isVisible = loadState is LoadState.Error
            binding.loadStateErrorMessage.isVisible = loadState is LoadState.Error
        }
    }
}