### Project 5: Resilient Task Runner (Exception Handling)

**Concepts:** `try/catch` in coroutines, `CoroutineExceptionHandler`, `SupervisorJob` vs regular `Job`, structured concurrency failure propagation.

**Description:** Build a "task runner" that executes several independent tasks concurrently (e.g., 5 fake API calls), where some are designed to randomly fail. Show the difference in behavior between a regular child-fails-parent-cancels setup and a `SupervisorJob` setup where one failure doesn't cancel siblings.

**Deliverable:** CLI app printing which tasks succeeded/failed and why, under both configurations.

**Resume value:** Demonstrates real understanding of structured concurrency's error model, a common junior-dev blind spot.