package org.example

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking<Unit>{
    val producersCount = 5
    val tasksPerProducerCount = 10
    val workersCount = 5

    val channel: Channel<Task> = Channel()

    val producers = buildList {
        repeat(producersCount) { producerId ->
            add(launch {
                repeat(tasksPerProducerCount) {
                    val taskId = producerId * tasksPerProducerCount + it
                    println("[Producer: $producerId] Sending job $taskId")
                    channel.send(Task(taskId, (1..3).random().seconds))
                }
            })
        }
    }

    repeat(workersCount) {
        launch {
            worker(it, channel)
        }
    }

    producers.joinAll()
    channel.close()
}


