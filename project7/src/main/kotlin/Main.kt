package org.example

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose Desktop App",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
    ) {
        val scope = rememberCoroutineScope()
        val stockDataProvider = StockDataProvider(scope)
        stockDataProvider.startEmitting()

        val allPricesViewModel = remember { AllPricesViewModel(stockDataProvider, scope) }
        val allPricesState = allPricesViewModel.state.collectAsState().value

        val highestPriceViewModel = remember { HighestPriceViewModel(stockDataProvider, scope) }
        val highestPriceState = highestPriceViewModel.state.collectAsState().value

        val notificationViewModel = remember { NotificationViewModel(stockDataProvider) }
        val notificationState = notificationViewModel.notifications

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            AllPricesSection(
                state = allPricesState,
                modifier = Modifier.weight(1f)
            )

            HighestPriceSection(
                state = highestPriceState,
                modifier = Modifier.weight(1f)
            )

            NotificationsSection(
                notificationState,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

