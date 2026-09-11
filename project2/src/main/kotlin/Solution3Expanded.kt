package org.example

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


class BetterFakeWeatherAPI: WeatherAPI {
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
        val (temperature, humidity, forecast) = awaitAll(
            a=async { fetchTemperature() },
            b=async { fetchHumidity() },
            c=async { fetchForecast() },
        )

        WeatherReport(temperature, humidity, forecast)
    }

}

suspend fun <A, B, C> awaitAll(
    a: Deferred<A>,
    b: Deferred<B>,
    c: Deferred<C>,
): Triple<A, B, C> = Triple(a.await(), b.await(), c.await())


