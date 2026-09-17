Text resources that I used:
- https://kotlinlang.org/docs/coroutines-guide.html
- https://kotlinlang.org/docs/coroutines-basics.html
- https://kotlinlang.org/docs/coroutines-cancellation.html
- https://kotlinlang.org/docs/coroutines-flow.html

Video guides that I used:

- [Threads vs. Kotlin Coroutines vs. Dispatchers by Philipp Lackner](https://www.youtube.com/watch?v=0Hv5LTxAutw)
- [Coroutines: Concurrency in Kotlin by Dave Leeds](https://www.youtube.com/watch?v=0Hv5LTxAutw)
- [Kotlin Coroutines Tutorial, Part 2: Cooperative Scheduling, Cancellation, Coroutine Context by Rock the JVM](https://www.youtube.com/watch?v=2RdHD0tceL4)
- [Hot Flows vs. Cold Flows In Kotlin by Philipp Lackner](https://www.youtube.com/watch?v=M8YtV47kaqA&t=18s)
- [Intro to Kotlin's Flow API by Dave Leeds](https://www.youtube.com/watch?v=6dOXCV_8nEI)


# Projects list:
## Tier 1 — Foundations (coroutine builders, suspend functions, dispatchers)

### Project 1: Concurrent Countdown Timers (CLI)
**Concepts:** `runBlocking`, `launch`, `delay`, suspend functions, sequential vs concurrent execution, basic coroutine builders.
**Description:** Build a command-line app that runs several countdown timers (e.g., "Timer A: 5s", "Timer B: 3s") concurrently, printing ticks to the console. First implement it sequentially (blocking) to see the problem, then convert to coroutines to run all timers in parallel.
**Deliverable:** A CLI Kotlin app with a `main` function using `runBlocking`, launching one coroutine per timer.
**Stretch goal:** Let the user add timers dynamically while others are running.
**Resume value:** Shows you understand the core difference coroutines make over blocking code.

### Project 2: Suspend Function Weather Simulator
**Concepts:** `suspend fun`, calling suspend functions from suspend functions, simulating I/O with `delay`, sequential composition.
**Description:** Write a fake "weather API" made of suspend functions (`fetchTemperature()`, `fetchHumidity()`, `fetchForecast()`), each with an artificial delay to simulate network latency. Combine them into a `getWeatherReport()` function and print a formatted report.
**Deliverable:** Small CLI app; measure and print total execution time.
**Stretch goal:** Compare execution time when calls are sequential vs when using `async`/`awaitAll` for the same calls.
**Resume value:** Demonstrates suspend function composition and awareness of latency.

### Project 3: Dispatcher Playground — Image/File Batch Processor
**Concepts:** `Dispatchers.IO`, `Dispatchers.Default`, `Dispatchers.Main` (concept only if no UI), `withContext`, CPU-bound vs I/O-bound work.
**Description:** Build a small app that reads a folder of text/image files, does an I/O-bound task (reading files) using `Dispatchers.IO`, and a CPU-bound task (e.g., word counting or basic image pixel processing) using `Dispatchers.Default`. Log the thread name at each step to prove correct dispatcher usage.
**Deliverable:** CLI tool with clear logs showing which dispatcher/thread handled which work.
**Stretch goal:** Benchmark performance difference between using the wrong dispatcher (e.g., IO work on Default) vs correct usage.
**Resume value:** Employers look for candidates who understand *why* dispatcher choice matters, not just syntax.

### Project 4: Cancellable Download Manager
**Concepts:** Structured concurrency, `Job`, cancellation (`cancel()`, `isActive`, cooperative cancellation), `withTimeout`/`withTimeoutOrNull`.
**Description:** Simulate downloading multiple files with progress reporting (0–100%). Allow the user to cancel an individual download or all downloads. Implement a timeout so downloads that take too long auto-cancel.
**Deliverable:** CLI app with commands like `start`, `cancel <id>`, `status`.
**Stretch goal:** Add pause/resume using coroutine suspension.
**Resume value:** Cancellation is one of the most-asked coroutine interview topics — a working demo stands out.

### Project 5: Resilient Task Runner (Exception Handling)
**Concepts:** `try/catch` in coroutines, `CoroutineExceptionHandler`, `SupervisorJob` vs regular `Job`, structured concurrency failure propagation.
**Description:** Build a "task runner" that executes several independent tasks concurrently (e.g., 5 fake API calls), where some are designed to randomly fail. Show the difference in behavior between a regular child-fails-parent-cancels setup and a `SupervisorJob` setup where one failure doesn't cancel siblings.
**Deliverable:** CLI app printing which tasks succeeded/failed and why, under both configurations.
**Resume value:** Demonstrates real understanding of structured concurrency's error model, a common junior-dev blind spot.

---

## Tier 2 — Flow, State, and Concurrency Primitives

### Project 6: Live Search with Debounce (Cold Flow)
**Concepts:** `Flow`, `flow {}` builder, cold vs hot streams, `debounce`, `distinctUntilChanged`, `map`, `collect`, `flatMapLatest`.
**Description:** Simulate a search-as-you-type feature: emit simulated keystrokes from a source, debounce input, filter duplicates, and "search" a fake in-memory dataset, cancelling stale searches when new input arrives.
**Deliverable:** CLI (or simple Compose Desktop UI) showing search results updating as "typed" input streams in.
**Stretch goal:** Add simulated network flakiness and retry with `retryWhen`.
**Resume value:** `flatMapLatest` + debounce is a very common real-world Android/Kotlin pattern.

### Project 7: Stock Ticker Dashboard (StateFlow & SharedFlow)
**Concepts:** `StateFlow` (state holder), `SharedFlow` (event bus), hot flows, `MutableStateFlow`, `stateIn`, multiple collectors.
**Description:** Build a simulated stock price ticker that emits random price updates for several stocks. Use `StateFlow` to hold "current price" state that multiple UI components (simulated as separate coroutines) observe, and `SharedFlow` to broadcast one-off events like "price alert triggered."
**Deliverable:** CLI or simple UI app with multiple independent "screens" (coroutines) reacting to the same state.
**Stretch goal:** Add a replay buffer and compare `SharedFlow(replay=1)` vs `StateFlow` behavior.
**Resume value:** StateFlow/SharedFlow are the backbone of modern Android state management (MVI/MVVM) — highly relevant to job listings.

### Project 8: Producer-Consumer Task Queue (Channels)
**Concepts:** `Channel`, `produce`, `actor` (or manual actor pattern), fan-out/fan-in, buffered vs unbuffered channels, `select`.
**Description:** Build a task processing system: one or more "producer" coroutines add jobs to a channel; a pool of "worker" coroutines consume and process jobs concurrently. Print throughput stats.
**Deliverable:** CLI app simulating a job queue with configurable worker count, showing load distribution.
**Stretch goal:** Use `select {}` to handle multiple channels (e.g., a priority queue plus a normal queue).
**Resume value:** Channels + worker pools is a classic concurrency interview question, made concrete.

### Project 9: Parallel API Aggregator
**Concepts:** `coroutineScope`, `supervisorScope`, `async`/`awaitAll`, structured concurrency for fan-out requests, partial failure handling.
**Description:** Build a "dashboard" that calls 4–5 independent fake APIs (weather, news, stocks, sports) concurrently and combines results into a single summary object, gracefully handling if one or two of the calls fail without failing the whole dashboard.
**Deliverable:** CLI app producing a combined report; include timing to show the calls ran in parallel, not sequentially.
**Resume value:** This mirrors real backend/Android use cases — combining multiple network calls efficiently and safely.

### Project 10: Testing Coroutines (Testing Suite for Projects 6–9)
**Concepts:** `kotlinx-coroutines-test`, `runTest`, `TestDispatcher`, `advanceTimeBy`/`advanceUntilIdle`, `Turbine` library for Flow testing.
**Description:** Go back to Projects 6–9 and write a proper unit test suite: test cancellation behavior, verify Flow emissions with Turbine, verify StateFlow updates, and test time-based logic (debounce, timeout) without real delays using virtual time.
**Deliverable:** A test module with meaningful coverage and comments explaining why each test matters.
**Resume value:** Testing coroutines correctly is rare among junior devs — this alone differentiates you.

---

## Tier 3 — Advanced Patterns & Production-Grade Design

### Project 11: Thread-Safe Bank Account Simulator (Actor Pattern / Mutex)
**Concepts:** Shared mutable state, race conditions, `Mutex`, actor pattern via `Channel`, comparing lock-based vs channel-based concurrency safety.
**Description:** Simulate a bank with multiple accounts and many concurrent "transactions" (transfers, deposits, withdrawals) hitting them at once. First demonstrate the race condition bug with naive shared state, then fix it two ways: with `Mutex`, and with an actor (a coroutine owning the state, receiving commands via a channel). Compare the two solutions.
**Deliverable:** CLI app plus a short write-up (README) comparing the two approaches' tradeoffs.
**Resume value:** Shows deep understanding of concurrency correctness, not just coroutine syntax.

### Project 12: Resilient HTTP Client Wrapper (Retry, Backoff, Circuit Breaker)
**Concepts:** Custom coroutine-based retry logic, exponential backoff with jitter, circuit breaker pattern, `Flow` for retry/backoff sequences, real networking with Ktor Client or Retrofit.
**Description:** Build a wrapper around a real HTTP client (Ktor recommended) that adds automatic retries with exponential backoff for transient failures, and a circuit breaker that stops calling a failing endpoint for a cooldown period. Test it against a real or mocked flaky API.
**Deliverable:** A small reusable library module with clear public API and README documenting usage.
**Stretch goal:** Publish it as a tiny open-source library on GitHub with tests and CI.
**Resume value:** This is genuinely production-relevant code — great "look what I built" portfolio piece.

### Project 13: Real-Time Sensor Data Pipeline (Flow Operators & Backpressure)
**Concepts:** `buffer`, `conflate`, `collectLatest`, backpressure strategies, `flatMapMerge`/`flatMapConcat`, custom `Flow` operators.
**Description:** Simulate a high-frequency sensor (e.g., 100 readings/sec) feeding a slower downstream consumer (e.g., writing to a "database" every 500ms). Demonstrate and compare different backpressure strategies (drop old data with `conflate`, buffer with limits, process only latest with `collectLatest`) and explain the tradeoffs of each with real numbers/logs.
**Deliverable:** CLI app with a report comparing throughput/data-loss for each strategy.
**Resume value:** Backpressure handling is an advanced, rarely-demonstrated skill that signals seniority beyond "junior."

### Project 14: Full Android/KMP App with Coroutines End-to-End
**Concepts:** Everything above integrated: ViewModel + `viewModelScope`, Repository pattern with suspend functions, Room (or SQLDelight for KMP) with Flow-based queries, Retrofit/Ktor for networking, `StateFlow` for UI state, structured error handling, coroutine-based caching layer.
**Description:** Build a complete small app (e.g., a weather app, a notes app with sync, or a news reader) using MVVM/MVI architecture. Coroutines should manage: network calls, local DB reads/writes as Flows, UI state exposure, and offline-first behavior (cache-then-network pattern).
**Deliverable:** A polished GitHub repo: README with architecture diagram, screenshots, and a short section titled "How coroutines are used in this project" explaining each usage.
**Stretch goal:** Make it Kotlin Multiplatform (share the coroutine-based data/domain layer between Android and iOS/Desktop).
**Resume value:** This is your flagship project — a full, realistic app is what recruiters and interviewers will actually look at first.

### Project 15 (Optional Capstone): Rate Limiter / Caching Library
**Concepts:** Custom `CoroutineContext` elements, coroutine-safe caching with `Mutex`/`Semaphore`, generic reusable API design, structured concurrency for resource cleanup, KDoc + publishing.
**Description:** Design and publish a small, general-purpose Kotlin library — e.g., a coroutine-based rate limiter (token bucket algorithm) or a suspend-function memoizing cache — that other developers could plug into their own coroutine-based projects.
**Deliverable:** Published to GitHub (optionally to Maven Central / JitPack) with full documentation, tests, and a usage example project.
**Resume value:** "I built and published a library" is a standout line for a junior developer, showing initiative and API design skill beyond typical coursework.
