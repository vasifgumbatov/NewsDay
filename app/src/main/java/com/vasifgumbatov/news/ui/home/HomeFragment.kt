package com.vasifgumbatov.news.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasifgumbatov.news.Adapters.NewsAdapter
import com.vasifgumbatov.news.ViewModel.NewsViewModel
import com.vasifgumbatov.news.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    //    private val newsViewModel: NewsViewModel by viewModels {
//        NewsViewModelFactory(GetNewsUseCase(ApiManager.newsApiService()))
//    }
    private val newsViewModel: NewsViewModel by viewModels()
    private var newsAdapter = NewsAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeRecyclerView.adapter = newsAdapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        newsViewModel.newsLiveData.observe(viewLifecycleOwner) { newsList ->
            newsAdapter = NewsAdapter(newsList)
            binding.homeRecyclerView.adapter = newsAdapter
        }
        observeNews()
        newsViewModel.fetchNews("bbc-news", "331cc6318d5f4e4bbdddfe9f3d4d6a93")
    }

    private fun observeNews() {
        newsViewModel.newsLiveData.observe(viewLifecycleOwner, Observer { articles ->
            for (article in articles) {
            }
        })
        newsViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
        })
    }
}
