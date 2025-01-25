package com.vasifgumbatov.news.ui.home.usanews

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
import com.vasifgumbatov.news.databinding.FragmentUsaBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class USAFragment : CoreFragment<FragmentUsaBinding>() {
    private val usaNewsVM: USANewsVM by viewModels()
    private var usaAdapter = USAAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUsaBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.backToHome?.setOnClickListener {
            findNavController().popBackStack()
        }

        setUpRecyclerViews()
        observeNews()
        usaNewsVM.fetchNewsBusiness(
            "us",
            "331cc6318d5f4e4bbdddfe9f3d4d6a93"
        )
    }

    private fun observeNews() {
        usaNewsVM.newsLiveData.observe(viewLifecycleOwner, Observer { newsList ->
            if (newsList.isNotEmpty()) {
                val latestNews = newsList.take(10)
                usaAdapter.submitList(latestNews)
                setupLikeClickListeners(latestNews)
            }
        })

        usaNewsVM.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            Log.e("Business News", "Error: $errorMessage")
        }
    }

    private fun setUpRecyclerViews() {
        usaAdapter = USAAdapter()
        usaAdapter.setOnItemClick { article ->
            val bundle = Bundle().apply {
                putString("author", article.author)
                putString("title", article.title)
                putString("imageUrl", article.urlToImage)
                putString("description", article.description)
                putString("url", article.url)
                putString("content", article.content)
                putString("publishedAt", article.publishedAt)
            }

            findNavController().navigate(R.id.action_usa_to_usaDetail, bundle)
        }

        binding?.businessRecyclerView?.apply {
            adapter = usaAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun setupLikeClickListeners(
        latestNews: List<Article>,
    ) {
        usaAdapter.setOnFavoriteClick { position ->
            val article = latestNews[position]
            if (article.isLiked) {
                usaNewsVM.removeBtcNewsFromDB(article)
                Toast.makeText(context, "Deleted from database", Toast.LENGTH_SHORT).show()
            } else {
                usaNewsVM.addBtcNewsToDB(article)
                Toast.makeText(context, "Added to database", Toast.LENGTH_SHORT).show()
            }

            article.isLiked = !article.isLiked
            usaAdapter.notifyItemChanged(position)
        }
    }
}