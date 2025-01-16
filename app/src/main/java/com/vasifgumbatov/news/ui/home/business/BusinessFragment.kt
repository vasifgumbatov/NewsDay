package com.vasifgumbatov.news.ui.home.business

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
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
import com.vasifgumbatov.news.databinding.FragmentBusinessBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import com.vasifgumbatov.news.ui.home.btcnews.BtcNewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessFragment : CoreFragment<FragmentBusinessBinding>() {
    private var businessAdapter = BusinessAdapter()
    private val businessVM: BusinessVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentBusinessBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.backToHome?.setOnClickListener {
            findNavController().popBackStack()
        }

        setUpRecyclerViews()
        observeNews()
        businessVM.fetchNewsBusiness(
            "us",
            "business",
            "331cc6318d5f4e4bbdddfe9f3d4d6a93"
        )
    }

    private fun observeNews() {
        businessVM.newsLiveData.observe(viewLifecycleOwner, Observer { newsList ->
            if (newsList.isNotEmpty()) {
                val latestNews = newsList.take(10)
                businessAdapter.submitList(latestNews)
                setupLikeClickListeners(latestNews)
            }
        })

        businessVM.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Log.e("Business News", "Error: $errorMessage")
        }
    }

    private fun setUpRecyclerViews() {
        businessAdapter = BusinessAdapter()
        businessAdapter.setOnItemClick { article ->
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

        binding?.businessRecyclerView?.apply {
            adapter = businessAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun setupLikeClickListeners(
        latestNews: List<Article>,
    ) {
        businessAdapter.setOnFavoriteClick { position ->
            latestNews[position].isLiked = !latestNews[position].isLiked
            businessAdapter.notifyItemChanged(position)
            businessVM.addBtcNewsToDB(latestNews[position])

            Toast.makeText(context, "Add successfully!", Toast.LENGTH_SHORT).show()
        }
    }
}