package org.example

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class HighestPriceViewModel(
    val stockDataProvider: StockDataProvider,
    val viewModelScope: CoroutineScope
) {
    private val _state = MutableStateFlow<HighestPriceState>(HighestPriceState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            stockDataProvider.prices
                .debounce {
                    2.seconds
                }
                .flatMapLatest { latestEmit ->
                    flow {
                        emit(latestEmit?.let { stockPrices ->
                            stockPrices.maxBy { it.price }
                        })
                    }
                }
                .collect { newValue ->
                    newValue?.let { v ->
                        _state.update { HighestPriceState.Loaded(v) }
                    }
                }
        }
    }
}


@Composable
fun HighestPriceSection(
    state: HighestPriceState,
    modifier: Modifier,
) {
    when (state){
        HighestPriceState.Loading ->
            Text("Loading prices...")

        is HighestPriceState.Loaded -> {
            Column(modifier) {
                Text("Highest price has: ${state.item.name} which is ${state.item.price}")
            }
        }
    }
}

sealed class HighestPriceState {
    data object Loading: HighestPriceState()

    data class Loaded(
        val item: StockItem
    ): HighestPriceState()
}



