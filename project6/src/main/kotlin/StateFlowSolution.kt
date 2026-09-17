package org.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel(private val scope: CoroutineScope) {
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _results = MutableStateFlow(Database.items)
    val results = _results.asStateFlow()

    fun onQueryChange(new: String) { _query.update { new } }

    init {
        scope.launch {
            _query
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
                .collect { newValue ->
                    _results.update { newValue }
                }
        }
    }
}



@Composable
fun StateFlowSolution() {
    val scope = rememberCoroutineScope()
    val viewModel = remember { SearchViewModel(scope) }
    val results = viewModel.results.collectAsState().value
    val query = viewModel.query.collectAsState().value


    MaterialTheme {
        Column {
            TextField(value = query, onValueChange = viewModel::onQueryChange)

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