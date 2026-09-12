import io.ktor.client.HttpClient
import kotlinx.coroutines.delay

suspend fun warmup(action: suspend (HttpClient) -> Unit) {
    println("Warming up JVM and connection pools...")
    val warmupClient = HttpClient()
    action(warmupClient)
    warmupClient.close()

    delay(1000)
}