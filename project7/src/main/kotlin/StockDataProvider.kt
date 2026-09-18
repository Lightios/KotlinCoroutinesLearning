package org.example

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class StockDataProvider(
    private val externalScope: CoroutineScope
) {
    private val _prices = MutableStateFlow<StockPrices?>(null)
    val prices = _prices.asStateFlow()

    private val _notifications = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 10
    )
    val notifications = _notifications.asSharedFlow()

    private val alreadyAlerted = mutableSetOf<String>()

    fun startEmitting() {
        externalScope.launch {
            while (isActive) {
                val newPrices = generateRandomPrices()
                _prices.emit(newPrices)
                checkForAlerts(newPrices)
                delay((1..5).random().seconds)
            }
        }
    }

    private fun generateRandomPrices(): StockPrices {
        return listOf(
            StockItem("AAPL", (100..2000).random()),
            StockItem("TSLA", (800..1700).random()),
            StockItem("GOOD", (1000..1500).random()),
        )
    }

    private suspend fun checkForAlerts(newPrices: StockPrices) {
        newPrices.forEach { stock ->
            val isHigh = stock.price > 1100
            val wasAlreadyAlerted = stock.name in alreadyAlerted

            if (isHigh && !wasAlreadyAlerted) {
                alreadyAlerted += stock.name
                _notifications.emit("${stock.name} has a high price ${stock.price}")
            } else if (!isHigh && wasAlreadyAlerted) {
                alreadyAlerted -= stock.name
            }
        }
    }
}

typealias StockPrices = List<StockItem>


data class StockItem(
    val name: String,
    val price: Int,
)