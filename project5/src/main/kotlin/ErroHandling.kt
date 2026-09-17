package org.example

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

// long waiting successful jobs are cancelled, error is NOT thrown
suspend fun scenario6() {
    try {
        coroutineScope {
            launch { successfulJob(1.seconds) }
            launch { successfulJob(3.seconds) }
            launch { successfulJob(4.seconds) }
            launch { failingJob(2.seconds) }
        }
    } catch (e: Throwable) {
        println(e)
        delay(6.seconds)
    }
}

// error is thrown, all jobs finish
suspend fun scenario7() {
    try {
        supervisorScope {
            launch { successfulJob(1.seconds) }
            launch { successfulJob(3.seconds) }
            launch { successfulJob(4.seconds) }
            launch { failingJob(2.seconds) }
        }
    } catch (e: Throwable) {
        println(e)
        delay(6.seconds)
    }
}

// Conclusion: coroutineScope propagates error from child to parent, supervisor job does not

// long waiting successful jobs are cancelled, error is thrown
suspend fun scenario8() {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, e -> println("Caught an exception via handler $e") }

    withContext(coroutineExceptionHandler) {
        coroutineScope {
            launch { successfulJob(1.seconds) }
            launch { successfulJob(3.seconds) }
            launch { successfulJob(4.seconds) }
            launch { failingJob(2.seconds) }
        }
    }
}

// error is NOT thrown, all jobs finish
suspend fun scenario9() {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, e -> println("Caught an exception via handler $e") }

    withContext(coroutineExceptionHandler) {
        supervisorScope {
            launch { successfulJob(1.seconds) }
            launch { successfulJob(3.seconds) }
            launch { successfulJob(4.seconds) }
            launch { failingJob(2.seconds) }
        }
    }
}


// long waiting successful jobs are cancelled, error is thrown -> same as scenario 8
suspend fun scenario10() {
    coroutineScope {
        val a = async { successfulJob(1.seconds) }
        val b = async { successfulJob(3.seconds) }
        val c = async { successfulJob(4.seconds) }
        val d = async { failingJob(2.seconds) }

        try { d.await() } catch (e: Throwable) { println("d failed: $e") }
    }
}

// error is NOT thrown, all jobs finish -> same as scenario 9
suspend fun scenario11() {
    supervisorScope {
        val a = async { successfulJob(1.seconds) }
        val b = async { successfulJob(3.seconds) }
        val c = async { successfulJob(4.seconds) }
        val d = async { failingJob(2.seconds) }

        try { d.await() } catch (e: Throwable) { println("d failed: $e") }
    }
}

// error is NOT thrown, all jobs finish
suspend fun scenario12() {
    coroutineScope {
        launch { runCatching { successfulJob(1.seconds) }.onFailure { println(it) } }
        launch { runCatching { successfulJob(3.seconds) }.onFailure { println(it) } }
        launch { runCatching { failingJob(2.seconds) }.onFailure { println(it) } }
    }
}

// error is NOT thrown, all jobs finish -> same as scenario 12
suspend fun scenario13() {
    supervisorScope {
        launch { runCatching { successfulJob(1.seconds) }.onFailure { println(it) } }
        launch { runCatching { successfulJob(3.seconds) }.onFailure { println(it) } }
        launch { runCatching { failingJob(2.seconds) }.onFailure { println(it) } }
    }
}



fun main() = runBlocking {
    scenario13()
}