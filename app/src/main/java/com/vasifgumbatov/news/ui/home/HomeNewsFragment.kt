package com.vasifgumbatov.news.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.data.remote.response.Article
import com.vasifgumbatov.news.databinding.FragmentHomeBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeNewsFragment : CoreFragment<FragmentHomeBinding>() {
    private val homeNewsVM: HomeNewsVM by viewModels()
    private var homeNewsAdapter = HomeNewsAdapter()
    private var otherNewsAdapter = HomeNewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btcBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_btcNewsFragment)
        }

        setUpRecyclerViews()
        observeNews()
        homeNewsVM.fetchNews("bbc-news", "331cc6318d5f4e4bbdddfe9f3d4d6a93")
    }

    private fun setUpRecyclerViews() {
        homeNewsAdapter = HomeNewsAdapter()
        otherNewsAdapter = HomeNewsAdapter()

        binding?.homeRecyclerView?.apply {
            adapter = homeNewsAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        binding?.recyclerOtherNews?.apply {
            adapter = otherNewsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding?.recyclerOtherNews?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {
                    binding?.btcBtn?.visibility = View.GONE
                    binding?.techCrunchBtn?.visibility = View.GONE
                    binding?.appleBtn?.visibility = View.GONE
                } else {
                    binding?.btcBtn?.visibility = View.VISIBLE
                    binding?.techCrunchBtn?.visibility = View.VISIBLE
                    binding?.appleBtn?.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun observeNews() {
        homeNewsVM.newsLiveData.observe(viewLifecycleOwner, Observer { newsList ->
            if (newsList.isNotEmpty()) {
                val latestNews = newsList.take(5)
                val otherNews = newsList.drop(5)

                homeNewsAdapter.submitList(latestNews)
                otherNewsAdapter.submitList(otherNews)

                setupLikeClickListeners(latestNews, otherNews)
            }
        })

        homeNewsVM.errorLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            Log.e("HomeNewsFragment", "Error: $errorMessage")
        })
    }

    private fun setupLikeClickListeners(
        latestNews: List<Article>,
        otherNews: List<Article>,
    ) {
        homeNewsAdapter.setOnItemClick { position ->
            latestNews[position].isLiked = !latestNews[position].isLiked
            homeNewsAdapter.notifyItemChanged(position)
            homeNewsVM.addMainNewsToDB(latestNews[position])
        }

        otherNewsAdapter.setOnItemClick { position ->
            otherNews[position].isLiked = !otherNews[position].isLiked
            otherNewsAdapter.notifyItemChanged(position)
            homeNewsVM.addMainNewsToDB(otherNews[position])
        }
    }
}