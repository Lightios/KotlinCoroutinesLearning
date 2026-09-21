package org.example

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds


@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking{
    val tasksCount = 10
    val workersCount = 5

    val channel: ReceiveChannel<Task> = produce {
        repeat(tasksCount) {
            println("Sending job $it")
            send(Task(it, (1..3).random().seconds))
        }
    }

    repeat(workersCount) {
        launch {
            worker(it, channel)
        }
    }
}
