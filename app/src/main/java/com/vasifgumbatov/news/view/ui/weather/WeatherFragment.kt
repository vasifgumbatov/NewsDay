package com.vasifgumbatov.news.view.ui.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.vasifgumbatov.news.databinding.FragmentWeatherBinding
import com.vasifgumbatov.news.view.ui.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment @Inject constructor() : CoreFragment<FragmentWeatherBinding>() {
    private val viewModel by viewModels<WeatherViewModel>()
    private var adapterWeather = WeatherAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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

        viewModel.getWeather(city, apiKey)
        viewModel.getWeatherData().observe(viewLifecycleOwner, Observer { weatherResponse ->
            weatherResponse?.let {
                val currentWeather = listOf(it.current)
                adapterWeather.updateData(currentWeather)
                binding?.cityText?.text = it.location.name
                binding?.tempText?.text = "${it.current.temperature} °C"
                binding?.weatherText?.text = it.current.condition.text
                binding?.weatherIcon?.contentDescription = it.current.condition.text
            }
        })

        binding?.searchButton?.setOnClickListener {
        }
    }
}