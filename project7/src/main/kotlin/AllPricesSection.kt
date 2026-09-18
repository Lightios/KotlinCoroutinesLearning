package org.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AllPricesViewModel(
    val stockDataProvider: StockDataProvider,
    val viewModelScope: CoroutineScope
) {
    private val _state = MutableStateFlow<AllPricesState>(AllPricesState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            stockDataProvider.prices
                .collect { newValue ->
                    newValue?.let { v ->
                        _state.update { AllPricesState.Loaded(v) }
                    }
                }
        }
    }
}


@Composable
fun AllPricesSection(
    state: AllPricesState,
    modifier: Modifier,
) {
    when (state){
        AllPricesState.Loading ->
            Text("Loading prices...")

        is AllPricesState.Loaded -> {
            LazyColumn(modifier) {
                items(state.stockPrices) {
                    Row {
                        Text("${it.name}: ${it.price}")
                    }
                }
            }
        }
    }
}

sealed class AllPricesState {
    data object Loading: AllPricesState()

    data class Loaded(
        val stockPrices: StockPrices
    ): AllPricesState()
}



