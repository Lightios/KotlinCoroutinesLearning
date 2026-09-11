package org.example

import kotlinx.coroutines.runBlocking
import kotlin.time.measureTimedValue

data class WeatherReport(
    val temperature: Int,
    val humidity: Int,
    val forecast: String
)


interface WeatherAPI {
    suspend fun fetchTemperature(): Int
    suspend fun fetchHumidity(): Int
    suspend fun fetchForecast(): String

    suspend fun getWeatherReport(): WeatherReport
}

fun main() {
    runBlocking {
//        val weatherApi = BadFakeWeatherAPI() // 6s
//        val weatherApi = GoodFakeWeatherAPI() // 2s
        val weatherApi = BetterFakeWeatherAPI() // 2s

        val (result, timeTaken) = measureTimedValue {
            weatherApi.getWeatherReport()
        }

        println(result)
        println("Time taken: $timeTaken")
    }
}