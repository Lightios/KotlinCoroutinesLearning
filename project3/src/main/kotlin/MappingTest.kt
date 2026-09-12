import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.measureTime

suspend fun map() {
    (1..50_000_000).map { it * it }
}


fun main () = runBlocking {
    val spawnCount = 100_000

    val time = measureTime {
        CoroutineScope(Dispatchers.Default).launch {
            repeat(spawnCount) {
                launch {
                    map()
                }
            }
        }
    }

    println(time)

    val time2 = measureTime {
        CoroutineScope(Dispatchers.IO).launch {
            repeat(spawnCount) {
                launch {
                    map()
                }
            }
        }
    }

    println(time2)
}