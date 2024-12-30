package com.vasifgumbatov.news.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vasifgumbatov.news.data.local.entity.FavoriteEntity
import com.vasifgumbatov.news.data.remote.response.Article
import com.vasifgumbatov.news.databinding.FragmentFavouriteBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteNewsFragment : CoreFragment<FragmentFavouriteBinding>() {
    private lateinit var favoriteAdapter: FavoriteNewsAdapter
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

        setUpRecyclerViews()
        observeNews()
        favoriteNewsVM.getFavorites()
    }

    private fun updateRecyclerView(filteredList: List<FavoriteEntity>) {
        favoriteAdapter = FavoriteNewsAdapter(filteredList.toMutableList())
        binding?.favoriteRV?.adapter = favoriteAdapter
    }

    private fun setUpRecyclerViews() {
        favoriteAdapter = FavoriteNewsAdapter(mutableListOf())
        binding?.favoriteRV?.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeNews() {
        favoriteNewsVM.newsLiveData.observe(viewLifecycleOwner) { data ->
            favoriteAdapter.updateList(data.reversed())
            allFavorites = data

            binding?.searchNews?.addTextChangedListener { editable ->
                val searchText = editable.toString().trim()
                val filteredList = allFavorites.filter {
                    it.title.contains(searchText, ignoreCase = true)
                }
                updateRecyclerView(filteredList)
            }
            setUpDeleteClickListeners()
        }
    }

    private fun setUpDeleteClickListeners() {
        favoriteAdapter.setOnDeleteClick { favorite ->
            favoriteAdapter.removeItem(favorite)
            favoriteNewsVM.deleteFavorite(favorite)

            Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show()
        }
    }
}
