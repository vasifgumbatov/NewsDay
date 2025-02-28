package com.vasifgumbatov.news.ui.weather

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.databinding.FragmentWeatherBinding
import com.vasifgumbatov.news.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : CoreFragment<FragmentWeatherBinding>() {
    private val weatherVM by viewModels<WeatherVM>()
    private var adapterWeather = WeatherAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterWeather = WeatherAdapter()

        val apiKey = "cd2d46eaef6f4c9db83105148241009"
        val city = "Baku"

        weatherVM.getWeather(city, apiKey)
        weatherVM.getWeatherData().observe(viewLifecycleOwner, Observer { weatherResponse ->
            weatherResponse?.let {
                val currentWeather = listOf(it.current)
                adapterWeather.updateData(currentWeather)

                binding?.cityText?.text = it.location.name
                binding?.tempText?.text = "${it.current.temperature} °C"
                binding?.weatherText?.text = it.current.condition.text
                binding?.lastUpdatedTV?.text = "Last Updated: ${it.current.lastUpdated}"
                binding?.windSpeed?.text = "${it.current.windSpeedK} kph"
                binding?.windDirection?.text = it.current.windDirection
                binding?.humidityText?.text = "${it.current.humidity} %"
                binding?.cloudInfoText?.text = "${it.current.cloud} %"

                Glide.with(requireContext())
                    .load("https:${it.current.condition.icon}")
                    .into(binding?.weatherIcon!!)
            }
        })

        binding?.searchCityInput?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                searchWeather()

                // close keyboard
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

                true
            } else {
                false
            }
        }


        binding?.searchButton?.setOnClickListener {
            searchWeather()

            // close keyboard
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding?.searchCityInput?.windowToken, 0)
        }
    }

    private fun searchWeather() {
        val query = binding?.searchCityInput?.text.toString().trim()
        if (query.isNotEmpty()) {
            weatherVM.getWeather(query, "cd2d46eaef6f4c9db83105148241009")
        } else {
            Toast.makeText(context, "Please enter city or country name!", Toast.LENGTH_SHORT).show()
        }
    }
}