# Project: Stock Ticker (Compose for Desktop)

## Goal
Build a simple Compose Desktop app with a simulated stock ticker, using `StateFlow` for shared price state and `SharedFlow` for one-off alert events, observed by multiple independent collectors.

## Core Components

**1. Price simulator (producer)**
- A coroutine loop that randomly nudges prices for a few stocks (e.g. `AAPL`, `TSLA`, `GOOG`) every 500ms–2s.
- Exposes current prices via `StateFlow`.

**2. Alert system (event bus)**
- When a price moves past some threshold, emit an event via `SharedFlow<AlertEvent>`.
- Not stored as state — just a transient notification.

**3. UI with multiple independent collectors**
- One composable showing live prices (updates instantly).
- One composable that collects the same `StateFlow` but "slowly" (e.g. artificial delay) — should visibly skip intermediate values.
- One composable/snackbar-like area showing alerts as they arrive from the `SharedFlow`.

**4. Lifecycle**
- Simulation runs in a `CoroutineScope` tied to the app; cancels cleanly on window close.

## Design Decisions to Make Yourself
- One `StateFlow` per stock, or one `StateFlow<Map<String, Double>>`?
- Where does alert-triggering logic live — inside the producer, or a separate collector?

## Stretch Goal
Add a second alert flow as `SharedFlow(replay = 1)`, add a late-subscribing collector, and compare what it receives vs. `replay = 0` and vs. a `StateFlow` holding the last alert.

## Deliverable
A runnable Compose Desktop app showing:
- Live-updating prices
- A visibly "laggy" collector demonstrating conflation
- Alerts appearing independently of price state