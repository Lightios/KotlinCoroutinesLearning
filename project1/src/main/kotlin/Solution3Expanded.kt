import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    var run = true
    runBlocking {
        launch(Dispatchers.IO) {
            while (run) {
                val input = readln()

                when {
                    input == "exit" -> {
                        run = false
                        cancel()
                    }

                    "[a-zA-Z]+ [0-9]+".toRegex().matches(input) -> {
                        val values = input.split(" ")
                        val name = values[0]
                        val time = values[1].toInt()

                        launch {
                            timer(name, time)
                        }
                    }
                }
            }
        }
    }
}