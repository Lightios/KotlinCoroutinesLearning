import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

suspend fun timer(name: String, value: Int) {
    repeat(value) {
        println("Timer $name: ${value - it}")
        delay(1.seconds)
    }
    println("Timer $name: 0")
}
