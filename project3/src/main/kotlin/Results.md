### Results

**Ktor Client**

Results with `delay=3000`
- without warmup
  - `Dispatchers.Default`: 4.250280900s
  - `Dispatchers.IO`: 3.476298s

- with warmup
  - `Dispatchers.Default`: 3.451859400s
  - `Dispatchers.IO`: 3.525382500s

Comments:
- without warmup first test is always slower (no matter which dispatcher it is)
- with warmup dispatchers works the same
- delay 0 makes results more random, but none of the dispatchers tends to be faster


**Java Client**

Results with `delay=0`
- without warmup
    - `Dispatchers.Default`: 856.878600ms
    - `Dispatchers.IO`: 898.553500ms
    - (second in the order tends to be faster)

- with warmup
    - `Dispatchers.Default`: 718.330800ms
    - `Dispatchers.IO`: 324.408500ms
    - `Dispatchers.IO` tends to be faster
  

Results with `delay=300`
- without warmup
    - `Dispatchers.Default`: 1.288912900s
    - `Dispatchers.IO`: 2.225950900s
    - `Dispatchers.IO` tends to be faster

- with warmup
    - `Dispatchers.Default`: 1.144018300s
    - `Dispatchers.IO`: 598.760800ms
    - `Dispatchers.IO` is much faster


Comments:
- without warmup first test in order is slower if no `delay` is set (no matter which dispatcher it is)
- with warmup `Dispatchers.IO` is faster
- `delay=300` (or more) shows that `Dispatchers.IO` is much faster


**Coroutines Spawn**

So far, the only scenario where `Dispatchers.Default` wins is spawning a lot of lightweight coroutines. 
`Default` wins, because it has lower thread pool resulting in less switching context between threads.

Example taken from StackOverflow: https://stackoverflow.com/questions/79399773/why-dispatchers-default-runs-faster-than-dispatchers-io-in-coroutines-for-simple
and adapted to the rest of my code.
