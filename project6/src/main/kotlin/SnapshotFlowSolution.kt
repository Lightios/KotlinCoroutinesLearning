package org.example

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.milliseconds


@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@Composable
fun SnapshotFlowSolution() {
    var query by remember { mutableStateOf("") }
    var results by remember { mutableStateOf(Database.items) }

    LaunchedEffect(Unit) {
        snapshotFlow { query }
            .debounce(
                200.milliseconds
            )
            .flatMapLatest { q ->
                flow {
                    println("search started: $q")
                    delay(600)
                    println("search completed: $q")
                    emit(Database.items.filter { it.contains(q, ignoreCase = true) })
                }
            }
            .collect {
                results = it
            }
    }

    MaterialTheme {
        Column {
            TextField(value = query, onValueChange = { query = it })

            LazyColumn {
                items(results) {
                    Row {
                        Text(it)
                    }
                }
            }
        }
    }
}