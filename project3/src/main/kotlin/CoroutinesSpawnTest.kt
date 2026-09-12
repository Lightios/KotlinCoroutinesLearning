import kotlinx.coroutines.*
import kotlin.time.measureTime

// Source - https://stackoverflow.com/q/79399773
// Posted by Roop Kishore
// Retrieved 2026-09-12, License - CC BY-SA 4.0
// Adapted from Android (Log/lifecycleScope) to plain Kotlin/JVM

suspend fun runLightweightJobs(dispatcher: CoroutineDispatcher, count: Int = 100) = coroutineScope {
    val jobs = mutableListOf<Job>()
    for (i in 1..count) {
        val job = launch(dispatcher) {
            val c = "c$i"
            println("Launched a coroutine $c on Thread: ${Thread.currentThread().name}")
            for (j in 1..10) {
                println("Coroutine $c, j = $j, Thread: ${Thread.currentThread().name}")
            }
        }
        jobs.add(job)
    }
    jobs.joinAll()
}

fun main() = runBlocking {
    println("Warming up...")
    runLightweightJobs(Dispatchers.IO, count = 20)
    runLightweightJobs(Dispatchers.Default, count = 20)
    delay(1000)

    println("\n--- Lightweight Coroutine Launch Benchmark (100 trivial jobs) ---")

    val defaultTime = measureTime {
        runLightweightJobs(Dispatchers.Default)
    }

    val ioTime = measureTime {
        runLightweightJobs(Dispatchers.IO)
    }


    println("Dispatchers.IO: $ioTime")
    println("Dispatchers.Default: $defaultTime")
}