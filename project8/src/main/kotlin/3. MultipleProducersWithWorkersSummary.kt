package org.example

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
fun main(args: Array<String>) = runBlocking<Unit>{
    val (workersCount, producersCount, tasksPerProducerCount) = parseConfig(args)

    val taskChannel = Channel<Task>()
    val resultsChannel = Channel<Result>()


    val producers = buildList {
        repeat(producersCount) { producerId ->
            add(launch {
                repeat(tasksPerProducerCount) {
                    val taskId = producerId * tasksPerProducerCount + it
                    println("[Producer: $producerId] Sending job $taskId")
                    taskChannel.send(Task(taskId, (1..3).random().seconds))
                }
            })
        }
    }

    val workers = buildList {
        repeat(workersCount) {
            add(launch {
                summaryWorker(it, taskChannel, resultsChannel)
            })
        }
    }


    launch {
        val summary = mutableMapOf<Int, Int>()
        for (result in resultsChannel) {
            summary[result.workerId] = summary.getOrDefault(result.workerId, 0) + 1
        }
        summarize(summary)
    }

    producers.joinAll()
    taskChannel.close()

    workers.joinAll()
    resultsChannel.close()
}


data class Result(
    val workerId: Int,
    val taskId: Int,
)

suspend fun summaryWorker(
    id: Int,
    taskChannel: ReceiveChannel<Task>,
    resultsChannel: Channel<Result>
) {
    for (task in taskChannel) {
        println("[Worker: $id] Started processing ${task.id}")
        delay(task.duration)
        println("[Worker: $id] Finished processing ${task.id}")

        resultsChannel.send(
            Result(workerId = id, taskId = task.id)
        )
    }
}

fun summarize(results: Map<Int, Int>) {
    results.entries.forEach {
        println("Worker ${it.key} completed ${it.value} tasks")
    }
}