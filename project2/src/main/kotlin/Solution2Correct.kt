package org.example

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


class GoodFakeWeatherAPI: WeatherAPI {
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

    override suspend fun getWeatherReport(): WeatherReport = coroutineScope {
        val temperature = async { fetchTemperature() }
        val humidity = async { fetchHumidity() }
        val forecast = async { fetchForecast() }

        WeatherReport(
            temperature = temperature.await(),
            humidity = humidity.await(),
            forecast = forecast.await()
        )
    }

}


