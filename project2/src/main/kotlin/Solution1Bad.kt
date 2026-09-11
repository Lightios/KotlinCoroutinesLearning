package org.example

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


class BadFakeWeatherAPI: WeatherAPI {
    override suspend fun fetchTemperature(): Int {
        println("Fetching temperature...")
        delay(2.seconds)
        println("Temperature fetched")
        return 14
    }

    override suspend fun fetchHumidity(): Int {
        println("Fetching humidity...")
        delay(1.5.seconds)
        println("Humidity fetched")
        return 94
    }

    override suspend fun fetchForecast(): String {
        println("Fetching forecast...")
        delay(2.seconds)
        println("Forecast fetched")
        return "Rain"
    }

    override suspend fun getWeatherReport(): WeatherReport {
        val temperature = fetchTemperature()
        val humidity = fetchHumidity()
        val forecast = fetchForecast()

        return WeatherReport(
            temperature = temperature,
            humidity = humidity,
            forecast = forecast
        )
    }
}


