package com.vasifgumbatov.news.ui.home.applenews

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
import com.vasifgumbatov.news.databinding.FragmentAppleNewsBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppleNewsFragment : CoreFragment<FragmentAppleNewsBinding>() {
    private val appleNewsVM: AppleNewsVM by viewModels()
    private var appleAdapter = AppleNewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAppleNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.backToHome?.setOnClickListener {
            findNavController().popBackStack()
        }

        setUpRecyclerViews()
        observeNews()
        appleNewsVM.fetchNewsApple(
            "apple",
            "331cc6318d5f4e4bbdddfe9f3d4d6a93",
            from = "2025-01-09",
            to = "2025-01-09",
            sortBy = "popularity",
        )
    }

    private fun observeNews() {
        appleNewsVM.newsLiveData.observe(viewLifecycleOwner, Observer { newsList ->
            if (newsList.isNotEmpty()) {
                val latestNews = newsList.take(10)

                appleAdapter.submitList(latestNews)

                setupLikeClickListeners(latestNews)
            }
        })

        appleNewsVM.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Log.e("AppleNews", "Error: $errorMessage")
        }
    }

    private fun setUpRecyclerViews() {
        // Set up the RecyclerView for BTC news
        appleAdapter = AppleNewsAdapter()

        appleAdapter.setOnItemClick { article ->
            val bundle = Bundle().apply {
                putString("author", article.author)
                putString("title", article.title)
                putString("imageUrl", article.urlToImage)
                putString("description", article.description)
                putString("url", article.url)
                putString("content", article.content)
                putString("publishedAt", article.publishedAt)
            }

            findNavController().navigate(R.id.action_appleNews_to_appleDetail, bundle)

        }

        binding?.appleRecyclerView?.apply {
            adapter = appleAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun setupLikeClickListeners(
        latestNews: List<Article>,
    ) {
        appleAdapter.setOnFavoriteClick { position ->
            latestNews[position].isLiked = !latestNews[position].isLiked
            appleAdapter.notifyItemChanged(position)
            appleNewsVM.addAppleNewsToDB(latestNews[position])

            Toast.makeText(context, "Add successfully!", Toast.LENGTH_SHORT).show()
        }
    }
}