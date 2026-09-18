package org.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.SharedFlow

class NotificationViewModel(
    val stockDataProvider: StockDataProvider,
) {
    val notifications = stockDataProvider.notifications

}


@Composable
fun NotificationsSection(
    notifications: SharedFlow<String>,
    modifier: Modifier,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    // Side effect: collect the event stream once, for the lifetime of this composable
    LaunchedEffect(Unit) {
        notifications.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text("Here will be displayed notifications")
        }
    }
}
