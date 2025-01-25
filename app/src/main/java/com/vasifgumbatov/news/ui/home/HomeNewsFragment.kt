package com.vasifgumbatov.news.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.data.remote.response.Article
import com.vasifgumbatov.news.databinding.FragmentHomeBinding
import com.vasifgumbatov.news.extensions.navigateFromParent
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
            navigateFromParent(R.id.action_main_to_btcNews)
        }

        binding?.techCrunchBtn?.setOnClickListener {
            navigateFromParent(R.id.action_main_to_techNews)
        }

        binding?.businessBtn?.setOnClickListener {
            navigateFromParent(R.id.action_main_to_usa)
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

        }

        binding?.recyclerOtherNews?.apply {
            adapter = otherNewsAdapter

        }

        homeNewsAdapter.setOnItemClick { article ->
            val bundle = Bundle().apply {
                putString("author", article.author)
                putString("title", article.title)
                putString("imageUrl", article.urlToImage)
                putString("description", article.description)
                putString("url", article.url)
                putString("content", article.content)
                putString("publishedAt", article.publishedAt)
            }

            navigateFromParent(R.id.action_main_to_homeDetail, bundle)
        }

        otherNewsAdapter.setOnItemClick { article ->
            val bundle = Bundle().apply {
                putString("author", article.author)
                putString("title", article.title)
                putString("imageUrl", article.urlToImage)
                putString("description", article.description)
                putString("url", article.url)
                putString("content", article.content)
                putString("publishedAt", article.publishedAt)
            }
            navigateFromParent(R.id.action_main_to_homeDetail, bundle)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeNews() {
        homeNewsVM.newsLiveData.observe(viewLifecycleOwner, Observer { newsList ->
            if (newsList.isNotEmpty()) {
                val latestNews = newsList.take(5)
                val otherNews = newsList.drop(5)

                binding?.progressBar?.visibility = View.GONE

                homeNewsAdapter.submitList(latestNews)
                otherNewsAdapter.submitList(otherNews)

                setupLikeClickListeners(latestNews, otherNews)
            }
        })

        homeNewsVM.errorLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            Log.e("HomeNewsFragment", "Error: $errorMessage")
        })

        homeNewsVM.favoriteRemovedLiveData.observe(viewLifecycleOwner) {
            homeNewsAdapter.notifyDataSetChanged()
            otherNewsAdapter.notifyDataSetChanged()
        }
    }

    private fun setupLikeClickListeners(
        latestNews: List<Article>,
        otherNews: List<Article>,
    ) {

        homeNewsAdapter.setOnFavoriteClick { position ->
            val article = latestNews[position]
            article.isLiked = !article.isLiked
            homeNewsAdapter.notifyItemChanged(position)

            if (article.isLiked) {
                homeNewsVM.addMainNewsToDB(article)
                Toast.makeText(context, "Added to database", Toast.LENGTH_SHORT).show()
            } else {
                homeNewsVM.removeBtcNewsFromDB(article)
                Toast.makeText(context, "Deleted from database", Toast.LENGTH_SHORT).show()
            }
        }

        otherNewsAdapter.setOnFavoriteClick { position ->
            val article = otherNews[position]
            article.isLiked = !article.isLiked
            otherNewsAdapter.notifyItemChanged(position)

            if (article.isLiked) {
                homeNewsVM.addMainNewsToDB(article)
                Toast.makeText(context, "Added to database", Toast.LENGTH_SHORT).show()
            } else {
                homeNewsVM.removeBtcNewsFromDB(article)
                Toast.makeText(context, "Deleted from database", Toast.LENGTH_SHORT).show()
            }
        }
    }
}