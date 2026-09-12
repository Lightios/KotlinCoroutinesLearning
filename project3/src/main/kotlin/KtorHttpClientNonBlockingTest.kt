import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import kotlinx.coroutines.*
import kotlin.time.measureTime



suspend fun fetchJsons(client: HttpClient, delay: Int = 0) = coroutineScope {
    val posts = buildList {
        repeat(100) { index ->
            add(async {
                client.get("https://dummyjson.com/products/${index + 1}?delay=$delay") {
                    header("Cache-Control", "no-cache, no-store")
                }
            })
        }
    }.awaitAll()

    println("Fetched ${posts.size} posts asynchronously")
}

fun main() = runBlocking {
    warmup { client ->
        fetchJsons(client)
    }

    val delay = 0

    val dispatchers = listOf(
        Dispatchers.IO,
        Dispatchers.Default,
    )

    for (dispatcher in dispatchers) {
        val client = HttpClient()

        val t = measureTime {
            withContext(dispatcher) {
                fetchJsons(client, delay=delay)
            }
        }

        println("$dispatcher: $t")
        client.close()
    }
}

