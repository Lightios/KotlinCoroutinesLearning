package org.example

import io.ktor.util.collections.ConcurrentMap
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.time.Duration.Companion.seconds


fun describeJob(id: Int, job: Job, percent: Int?) {
    print("Job with id=$id is ")
    if (job.isActive) {
        print("active with ${percent ?: 0}%")
    } else if (job.isCancelled) {
        print("cancelled")
    } else {
        print("completed")
    }
    println("")
}

suspend fun downloadPieceOfFile() {
    // just simulate
    delay((1..3).random().seconds)
}


suspend fun download(id: Int, totalSteps: Int = 10, onProgress: (Int) -> Unit) {
    for (progress in 1..totalSteps) {
        downloadPieceOfFile()
        val percent = progress * 100 / totalSteps
        println("[id=$id] Downloading $percent%...")
        onProgress(percent)

        delay(0.1.seconds) // yield to be able to get cancelled
    }
}

class DownloadingMonitor {
    val mutex = Mutex()
    val monitorScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val idToJob = mutableMapOf<Int, Job>()
    private val idToProgress = ConcurrentMap<Int, Int>()

    suspend fun startNewDownload() = mutex.withLock {
        val newId = (idToJob.maxOfOrNull { it.key } ?: -1) + 1
        val downloadingJob = monitorScope.launch {
            withTimeout(23.seconds) {
                    download(newId) {
                        idToProgress[newId] = it
                    }
                }
            }
        idToJob[newId] = downloadingJob
    }

    suspend fun cancelJob(id: Int) = mutex.withLock {
        val jobExists = idToJob[id] != null
        if (jobExists) {
            idToJob[id]?.cancel()
        }

        jobExists
    }

    suspend fun describeJobs() = mutex.withLock {
        for ((id, job) in idToJob.entries) {
            describeJob(id, job, idToProgress[id])
        }
    }

    suspend fun exit() {
        monitorScope.cancel()
        monitorScope.coroutineContext.job.join()
    }
}

@OptIn(ExperimentalAtomicApi::class)
fun main() = runBlocking {
    val monitor = DownloadingMonitor()
    val run = AtomicBoolean(true)

    launch(Dispatchers.IO) {
        while (run.load()) {
            val input = readln()

            when {
                input == "exit" -> {
                    run.store(false)
                    monitor.exit()
                }
                input == "start" -> {
                    monitor.startNewDownload()
                }

                input == "status" -> {
                    monitor.describeJobs()
                }

                "cancel [0-9]+".toRegex().matches(input) -> {
                    val id = input.split(" ").last().toInt()
                    monitor.cancelJob(id).also {
                        if (!it) {
                            println(("No job with given id was found"))
                        }
                    }
                }
            }
        }
    }

    Unit
}
