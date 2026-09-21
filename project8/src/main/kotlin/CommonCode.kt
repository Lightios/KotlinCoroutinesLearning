package org.example

import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlin.time.Duration


suspend fun worker(id: Int, channel: ReceiveChannel<Task>) {
    for (task in channel) {
        println("[Worker: $id] Started processing ${task.id}")
        delay(task.duration)
        println("[Worker: $id] Finished processing ${task.id}")
    }
}


data class Task(
    val id: Int,
    val duration: Duration
)

data class Config(
    val workers: Int = 5,
    val producers: Int = 5,
    val tasksPerProducer: Int = 10,
) {
    init {
        require(workers > 0) { "workers must be > 0" }
        require(producers > 0) { "producers must be > 0" }
        require(tasksPerProducer > 0) { "tasks must be > 0" }
    }
}

fun parseConfig(args: Array<String>): Config {
    var config = Config()
    var i = 0
    while (i < args.size) {
        val option = args[i]
        val value = args.getOrNull(i + 1)?.toIntOrNull()
            ?: error("Missing or non-numeric value for $option")
        config = when (option) {
            "--workers" -> config.copy(workers = value)
            "--producers" -> config.copy(producers = value)
            "--tasks" -> config.copy(tasksPerProducer = value)
            else -> error("Unknown option $option")
        }
        i += 2
    }
    return config
}
