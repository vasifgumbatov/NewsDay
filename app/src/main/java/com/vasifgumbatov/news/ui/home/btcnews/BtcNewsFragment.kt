package com.vasifgumbatov.news.ui.home.btcnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.data.remote.response.Article
import com.vasifgumbatov.news.databinding.FragmentBtcNewsBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BtcNewsFragment : CoreFragment<FragmentBtcNewsBinding>() {
    private val btcNewsVM: BtcNewsVM by viewModels()
    private var btcNewsAdapter = BtcNewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBtcNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.backToHome?.setOnClickListener {
            findNavController().popBackStack()
        }

        setUpRecyclerViews()
        observeNews()
        setupSwipeRefresh()
        btcNewsVM.fetchNewsBtc(
            "bitcoin",
            "331cc6318d5f4e4bbdddfe9f3d4d6a93"
        )
    }

    // Observe news articles
    private fun observeNews() {
        btcNewsVM.newsLiveData.observe(viewLifecycleOwner, Observer { newsList ->
            if (newsList.isNotEmpty()) {
                val latestNews = newsList.take(10)
                btcNewsAdapter.submitList(latestNews)
                setupLikeClickListeners(latestNews)
            }
        })

        btcNewsVM.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Log.e("Btc News", "Error: $errorMessage")
        }
    }

    private fun setUpRecyclerViews() {
        btcNewsAdapter = BtcNewsAdapter()
        btcNewsAdapter.setOnItemClick { article ->
            val bundle = Bundle().apply {
                putString("author", article.author)
                putString("title", article.title)
                putString("imageUrl", article.urlToImage)
                putString("description", article.description)
                putString("url", article.url)
                putString("content", article.content)
                putString("publishedAt", article.publishedAt)
            }

            findNavController().navigate(R.id.action_btcNews_to_btcDetail, bundle)
        }

        binding?.btcRecyclerView?.apply {
            adapter = btcNewsAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun setupLikeClickListeners(
        latestNews: List<Article>,
    ) {
        btcNewsAdapter.setOnFavoriteClick { position ->
            val article = latestNews[position]
            if (article.isLiked) {
                btcNewsVM.removeBtcNewsFromDB(article)
                Toast.makeText(context, "Deleted from database", Toast.LENGTH_SHORT).show()
            } else {
                btcNewsVM.addBtcNewsToDB(article)
                Toast.makeText(context, "Added to database", Toast.LENGTH_SHORT).show()
            }

            article.isLiked = !article.isLiked
            btcNewsAdapter.notifyItemChanged(position)
        }
    }

    private fun setupSwipeRefresh() {
        binding?.swipeRefreshLayout?.setOnRefreshListener {
            btcNewsVM.fetchNewsBtc(
                "bitcoin",
                "331cc6318d5f4e4bbdddfe9f3d4d6a93"
            )
            binding?.swipeRefreshLayout?.isRefreshing = false
        }
    }
}