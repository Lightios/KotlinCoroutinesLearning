### Project 2: Suspend Function Weather Simulator

**Concepts:** `suspend fun`, calling suspend functions from suspend functions, simulating I/O with `delay`, sequential composition.

**Description:** Write a fake "weather API" made of suspend functions (`fetchTemperature()`, `fetchHumidity()`, `fetchForecast()`), each with an artificial delay to simulate network latency. Combine them into a `getWeatherReport()` function and print a formatted report.

**Deliverable:** Small CLI app; measure and print total execution time.

**Stretch goal:** Compare execution time when calls are sequential vs when using `async`/`awaitAll` for the same calls.

**Resume value:** Demonstrates suspend function composition and awareness of latency.