package com.vasifgumbatov.news.ui.weather

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vasifgumbatov.news.data.remote.response.CurrentWeather
import com.vasifgumbatov.news.databinding.ItemWeatherBinding

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private var weatherList: List<CurrentWeather> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<CurrentWeather>) {
        weatherList = newList.toMutableList()
        notifyDataSetChanged()
    }

    inner class WeatherViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weather: CurrentWeather) {
            with(binding) {
                tempText.text = weather.temperature.toString()
                weatherText.text = weather.condition.text
                weatherIcon.contentDescription = weather.condition.text
                cityText.text = weather.condition.text
                lastUpdatedTV.text = weather.lastUpdated
            }

            Glide.with(binding.weatherIcon.context)
                .load(weather.condition.icon)
                .into(binding.weatherIcon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherBinding.inflate(inflater, parent, false)
        return WeatherViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.bind(weather)
    }
}