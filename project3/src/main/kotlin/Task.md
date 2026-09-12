### Project 3: Dispatcher Playground — Image/File Batch Processor

**Concepts:** `Dispatchers.IO`, `Dispatchers.Default`, `Dispatchers.Main` (concept only if no UI), `withContext`, CPU-bound vs I/O-bound work.

**Description:** Build a small app that reads a folder of text/image files, does an I/O-bound task (reading files) using `Dispatchers.IO`, and a CPU-bound task (e.g., word counting or basic image pixel processing) using `Dispatchers.Default`. Log the thread name at each step to prove correct dispatcher usage.

**Deliverable:** CLI tool with clear logs showing which dispatcher/thread handled which work.

**Stretch goal:** Benchmark performance difference between using the wrong dispatcher (e.g., IO work on Default) vs correct usage.

**Resume value:** Employers look for candidates who understand *why* dispatcher choice matters, not just syntax.