package com.vasifgumbatov.news.data.remote.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    var location: WeatherLocation,
    var current: CurrentWeather
)

data class WeatherLocation(
    var name: String,
    var region: String,
    var country: String
)

data class CurrentWeather(
    @SerializedName("temp_c")
    var temperature: Double,
    @SerializedName("temp_f")
    var temperatureFahrenheit: Double,
    @SerializedName("condition")
    var condition: Condition,
    @SerializedName("last_updated")
    var lastUpdated: String,
    @SerializedName("wind_kph")
    var windSpeedK: Double,
    @SerializedName("wind_dir")
    var windDirection: String,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("cloud")
    var cloud: Int
)

data class Condition(
    var text: String,
    var icon: String
)
