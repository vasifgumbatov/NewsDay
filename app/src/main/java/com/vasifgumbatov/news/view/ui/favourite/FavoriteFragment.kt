package com.vasifgumbatov.news.view.ui.favourite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasifgumbatov.news.databinding.FragmentFavouriteBinding
import com.vasifgumbatov.news.view.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : CoreFragment<FragmentFavouriteBinding>() {
    private var favoriteAdapter = FavoriteNewsAdapter(emptyList())
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.favoriteRV?.adapter = favoriteAdapter
        binding?.favoriteRV?.layoutManager = LinearLayoutManager(requireContext())

        favoriteViewModel.getFavorites()
        favoriteViewModel.getLikedNews()

        // Observe favorite items in the ViewModel
        favoriteViewModel.newsLiveData.observe(viewLifecycleOwner) { data ->

            Log.d("Favorite", "onViewCreated: " + data.size)
            favoriteAdapter = FavoriteNewsAdapter(data)
        }
    }
}