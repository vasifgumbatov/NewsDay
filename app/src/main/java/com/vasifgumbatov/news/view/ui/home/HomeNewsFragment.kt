package com.vasifgumbatov.news.view.ui.home

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.databinding.FragmentHomeBinding
import com.vasifgumbatov.news.view.ui.core.CoreFragment
import com.vasifgumbatov.news.view.ui.favourite.FavoriteNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeNewsFragment @Inject constructor() : CoreFragment<FragmentHomeBinding>() {
    private val homeNewsVM: HomeNewsVM by viewModels()
    private var homeNewsAdapter = HomeNewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeNewsAdapter = HomeNewsAdapter()
        binding?.homeRecyclerView?.adapter = homeNewsAdapter
        binding?.homeRecyclerView?.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        homeNewsVM.newsLiveData.observe(viewLifecycleOwner) { newsList ->
            homeNewsAdapter.submitList(newsList)

            homeNewsAdapter.setOnItemClick { position ->

                newsList[position].isLiked = !newsList[position].isLiked
                homeNewsAdapter.notifyItemChanged(position)

                homeNewsVM.addToDB(newsList[position])
            }
            binding?.homeRecyclerView?.adapter = homeNewsAdapter
        }

        observeNews()
        homeNewsVM.fetchNews("bbc-news", "331cc6318d5f4e4bbdddfe9f3d4d6a93")
    }

    private fun observeNews() {
        homeNewsVM.newsLiveData.observe(viewLifecycleOwner, Observer { article ->
            homeNewsAdapter.submitList(article)
        })
        homeNewsVM.errorLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            Log.e("HomeNewsFragment", "Error: $errorMessage")
        })
    }
}
