### Project 6: Live Search with Debounce (Cold Flow)

**Concepts:** `Flow`, `flow {}` builder, cold vs hot streams, `debounce`, `distinctUntilChanged`, `map`, `collect`, `flatMapLatest`.

**Description:** Simulate a search-as-you-type feature: emit simulated keystrokes from a source, debounce input, filter duplicates, and "search" a fake in-memory dataset, cancelling stale searches when new input arrives.

**Deliverable:** CLI (or simple Compose Desktop UI) showing search results updating as "typed" input streams in.

**Stretch goal:** Add simulated network flakiness and retry with `retryWhen`.

**Resume value:** `flatMapLatest` + debounce is a very common real-world Android/Kotlin pattern.