import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() {
   runBlocking {
       launch {
           timer("A", 5)
       }

       launch {
           timer("B", 3)
       }
   }
}