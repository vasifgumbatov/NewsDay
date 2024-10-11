package com.vasifgumbatov.news.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vasifgumbatov.news.R
import com.vasifgumbatov.news.databinding.FragmentMainBinding
import com.vasifgumbatov.news.ui.favourite.FavoriteFragment
import com.vasifgumbatov.news.ui.home.HomeFragment
import com.vasifgumbatov.news.ui.profile.ProfileFragment
import com.vasifgumbatov.news.ui.weather.WeatherFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.homePage -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.favouritePage -> {
                    replaceFragment(FavoriteFragment())
                    true
                }
                R.id.profilePage -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                R.id.weatherPage -> {
                    replaceFragment(WeatherFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.bottomMenuContainer, fragment)
            .commit()
    }

}
