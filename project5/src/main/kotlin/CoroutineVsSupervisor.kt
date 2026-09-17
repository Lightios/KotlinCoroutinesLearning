package org.example

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

suspend fun successfulJob(delay: Duration = 2.seconds, id: Int = 1): Int {
    delay(delay)
    println("Successful Job $id is done!")
    return 1
}

suspend fun failingJob(delay: Duration = 2.seconds, id: Int = 2): Int {
    delay(delay)
    throw Error("Job $id failed")
}


// Here every running coroutine is cancelled when job fails
suspend fun scenario1() {
    coroutineScope {
        launch { successfulJob(1.seconds) }
        launch { successfulJob(3.seconds) }
        launch { successfulJob(5.seconds) }
        launch { failingJob(2.seconds) }
        launch { failingJob(4.seconds) }
    }
}

// Here every successful coroutine will finish properly, meanwhile failing will throw an error
suspend fun scenario2() {
    supervisorScope {
        launch { successfulJob(1.seconds) }
        launch { successfulJob(3.seconds) }
        launch { successfulJob(5.seconds) }
        launch { failingJob(2.seconds) }
        launch { failingJob(4.seconds) }
    }
}

// Throws an error, cancels second successful job
suspend fun scenario3() {
    coroutineScope {
        val result = async { successfulJob(1.seconds) }
        val result2 = async { failingJob(2.seconds) }
        val result3 = async { failingJob(4.seconds) }
    }
}


// Successful jobs both finish
suspend fun scenario4() {
    supervisorScope {
        val result = async { successfulJob(1.seconds) }
        val result2 = async { failingJob(2.seconds) }
        val result3 = async { failingJob(4.seconds) }
    }
}


// Await throws an error and second job is cancelled
suspend fun scenario5() {
    supervisorScope {
        val result = async { successfulJob(1.seconds) }
        val result2 = async { failingJob(2.seconds) }
        val result3 = async { failingJob(4.seconds) }

        delay(1.seconds)
        result2.await()
    }
}



fun main() = runBlocking {
    scenario5()
}