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
    @SerializedName("condition")
    var condition: Condition,
    @SerializedName("last_updated")
    var lastUpdated: String
)

data class Condition(
    var text: String,
    var icon: String
)
