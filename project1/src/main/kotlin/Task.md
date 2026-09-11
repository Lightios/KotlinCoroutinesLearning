
### Project 1: Concurrent Countdown Timers (CLI)
**Concepts:** `runBlocking`, `launch`, `delay`, suspend functions, sequential vs concurrent execution, basic coroutine builders.

**Description:** Build a command-line app that runs several countdown timers (e.g., "Timer A: 5s", "Timer B: 3s") concurrently, printing ticks to the console. First implement it sequentially (blocking) to see the problem, then convert to coroutines to run all timers in parallel.

**Deliverable:** A CLI Kotlin app with a `main` function using `runBlocking`, launching one coroutine per timer.

**Stretch goal:** Let the user add timers dynamically while others are running (done as **Solution 3**).

**Resume value:** Shows you understand the core difference coroutines make over blocking code.