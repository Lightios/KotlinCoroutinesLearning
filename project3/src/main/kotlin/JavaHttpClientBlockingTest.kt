import kotlinx.coroutines.*
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.time.measureTime

// Single shared Java HttpClient instance
val javaClient: HttpClient = HttpClient.newHttpClient()

// Synchronous blocking network call
fun fetchSinglePostSync(index: Int, delay: Int): String {
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://dummyjson.com/products/${index + 1}?delay=$delay"))
        .header("Cache-Control", "no-cache")
        .GET()
        .build()

    // .send() BLOCKS the current thread waiting for network response
    val response = javaClient.send(request, HttpResponse.BodyHandlers.ofString())
    return response.body()
}

suspend fun fetchJsonsBlocking(delay: Int = 0) = coroutineScope {
    val posts = buildList {
        repeat(50) { index ->
            add(async {
                fetchSinglePostSync(index, delay)
            })
        }
    }.awaitAll()

    println("Fetched ${posts.size} posts synchronously")
}

fun main() = runBlocking {
    // Warm up JVM, class loading, and thread pools
//    println("Warming up JVM...")
//    fetchJsonsBlocking()
//    delay(1000)

    val delay = 300

    val dispatchers = listOf(
        Dispatchers.IO,       // Up to 64 threads
        Dispatchers.Default, // ~8 threads (depends on CPU cores)
    )

    for (dispatcher in dispatchers) {
        val time = measureTime {
            withContext(dispatcher) {
                fetchJsonsBlocking(delay)
            }
        }

        println("$dispatcher: $time")
    }
}