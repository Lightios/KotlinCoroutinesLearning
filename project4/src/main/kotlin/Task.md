### Project 4: Cancellable Download Manager

**Concepts:** Structured concurrency, `Job`, cancellation (`cancel()`, `isActive`, cooperative cancellation), `withTimeout`/`withTimeoutOrNull`.

**Description:** Simulate downloading multiple files with progress reporting (0–100%). Allow the user to cancel an individual download or all downloads. Implement a timeout so downloads that take too long auto-cancel.

**Deliverable:** CLI app with commands like `start`, `cancel <id>`, `status`.

**Stretch goal:** Add pause/resume using coroutine suspension.

**Resume value:** Cancellation is one of the most-asked coroutine interview topics — a working demo stands out.
