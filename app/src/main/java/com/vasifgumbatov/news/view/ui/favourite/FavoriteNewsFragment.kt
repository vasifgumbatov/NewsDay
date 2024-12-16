package com.vasifgumbatov.news.view.ui.favourite

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.databinding.FragmentFavouriteBinding
import com.vasifgumbatov.news.view.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteNewsFragment @Inject constructor() : CoreFragment<FragmentFavouriteBinding>() {
    private var favoriteAdapter = FavoriteNewsAdapter(emptyList())
    private val favoriteNewsVM: FavoriteNewsVM by viewModels()
    private var allFavorites = emptyList<FavoriteEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteNewsVM.getFavorites()
        favoriteNewsVM.getLikedNews()

        favoriteNewsVM.newsLiveData.observe(viewLifecycleOwner) { data ->

            favoriteAdapter.setOnItemClick { position ->
                favoriteNewsVM.deleteFromDB(data[position])
            }

            binding?.favoriteRV?.layoutManager = LinearLayoutManager(requireContext())
            favoriteAdapter = FavoriteNewsAdapter(data.reversed())
            binding?.favoriteRV?.adapter = favoriteAdapter
            allFavorites = data

            binding?.searchNews?.addTextChangedListener { editable ->
                val searchText = editable.toString().trim()
                val filteredList = allFavorites.filter {
                    it.title.contains(searchText, ignoreCase = true)
                }
                updateRecyclerView(filteredList)
            }
        }
    }

    private fun updateRecyclerView(filteredList: List<FavoriteEntity>) {
        favoriteAdapter = FavoriteNewsAdapter(filteredList)
        binding?.favoriteRV?.adapter = favoriteAdapter
    }

}
