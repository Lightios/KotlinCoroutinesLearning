### Project 8: Producer-Consumer Task Queue (Channels)

**Concepts:** `Channel`, `produce`, `actor` (or manual actor pattern), fan-out/fan-in, buffered vs unbuffered channels, `select`.

**Description:** Build a task processing system: one or more "producer" coroutines add jobs to a channel; a pool of "worker" coroutines consume and process jobs concurrently. Print throughput stats.

**Deliverable:** CLI app simulating a job queue with configurable worker count, showing load distribution.

**Stretch goal:** Use `select {}` to handle multiple channels (e.g., a priority queue plus a normal queue).

**Resume value:** Channels + worker pools is a classic concurrency interview question, made concrete.