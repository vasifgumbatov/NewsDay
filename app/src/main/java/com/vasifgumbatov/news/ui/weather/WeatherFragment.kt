package com.vasifgumbatov.news.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vasifgumbatov.news.Adapters.WeatherAdapter
import com.vasifgumbatov.news.ViewModel.WeatherViewModel
import com.vasifgumbatov.news.databinding.FragmentWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment @Inject constructor() : Fragment() {

    private lateinit var searchInput: EditText
    private lateinit var searchButton: ImageButton
    private lateinit var binding: FragmentWeatherBinding
    private val viewModel by viewModels<WeatherViewModel>()
    private var adapterWeather = WeatherAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterWeather = WeatherAdapter()

        val apiKey = "cd2d46eaef6f4c9db83105148241009"
        val city = "Baku"

        viewModel.getWeather(city, apiKey)
        viewModel.getWeatherData().observe(viewLifecycleOwner, Observer { weatherResponse ->
            weatherResponse?.let {
                val currentWeather = listOf(it.current)
                adapterWeather.updateData(currentWeather)
                binding.cityText.text = it.location.name
                binding.tempText.text = "${it.current.temperature} °C"
                binding.weatherText.text = it.current.condition.text
                binding.weatherIcon.contentDescription = it.current.condition.text
            }
        })

        binding.searchButton.setOnClickListener {
            val cityName = searchInput.text.toString().trim()
            if (cityName.isNotEmpty()) {
                performSearch(cityName)
            }else {
                Toast.makeText(context, "Please enter a valid city or country", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performSearch(cityName: String) {
        viewModel.getWeather(cityName, "cd2d46eaef6f4c9db83105148241009")
    }
}