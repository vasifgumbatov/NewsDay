package com.vasifgumbatov.news.ui.home.techcrunchnews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.data.remote.response.Article
import com.vasifgumbatov.news.databinding.FragmentTechCrunchNewsBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TechCrunchNewsFragment : CoreFragment<FragmentTechCrunchNewsBinding>() {
    private val techNewsVM: TechCrunchViewModel by viewModels()
    private var techAdapter = TechCrunchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTechCrunchNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerViews()
        observeTechVM()
        techNewsVM.fetchTechNews("techcrunch", "331cc6318d5f4e4bbdddfe9f3d4d6a93")
    }

    private fun observeTechVM() {
        techNewsVM.newsLiveData.observe(viewLifecycleOwner, Observer { newsList ->
            if (newsList.isNotEmpty()) {
                val latestNews = newsList.take(10)

                techAdapter.submitList(latestNews)

                setupLikeClickListeners(latestNews)
            }
        })

        techNewsVM.errorLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            Log.e("BtcNews", "Error: $errorMessage")
        })
    }

    private fun setUpRecyclerViews() {
        techAdapter = TechCrunchAdapter()

        techAdapter.setOnItemClick { article ->
            val bundle = Bundle().apply {
                putString("author", article.author)
                putString("title", article.title)
                putString("imageUrl", article.urlToImage)
                putString("description", article.description)
                putString("url", article.url)
                putString("content", article.content)
                putString("publishedAt", article.publishedAt)
            }

            findNavController().navigate(R.id.action_techCrunchNews_to_techCrunchDetail, bundle)

        }

        binding?.techRecyclerView?.apply {
            adapter = techAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun setupLikeClickListeners(
        latestNews: List<Article>,
    ) {
        techAdapter.setOnFavoriteClick { position ->
            latestNews[position].isLiked = !latestNews[position].isLiked
            techAdapter.notifyItemChanged(position)
            techNewsVM.addTechNewsToDB(latestNews[position])
        }
    }
}