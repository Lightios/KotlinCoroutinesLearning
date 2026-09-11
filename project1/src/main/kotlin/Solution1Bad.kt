import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        timer("A", 5)
        timer("B", 3)
    }
}