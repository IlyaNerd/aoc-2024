import kotlin.math.log10
import kotlin.math.pow
import kotlin.time.measureTimedValue

fun main() {
    fun blink(input: List<Long>, times: Int): Long {
        val cache = mutableMapOf<Pair<Long, Int>, Long>()

        fun size(lng: Long, iterations: Int): Long {
            val key = lng to iterations
            cache[key]?.let { return it }
            val result = if (iterations == times) 0
            else if (lng == 0L) {
                size(1L, iterations + 1)
            } else {
                val length = log10(lng.toDouble()).toInt() + 1
                if (length % 2 == 0) {
                    val div = 10.0.pow(length / 2.0).toLong()
                    size(lng / div, iterations + 1) + size(lng % div, iterations + 1) + 1
                } else {
                    size(lng * 2024L, iterations + 1)
                }
            }
            cache[key] = result
            return result
        }

        return input.sumOf { lng -> size(lng, 0) } + input.size
    }

    fun part1(input: List<String>): Long {
        val numbers = input.first().let { it.split(" ").map { it.toLong() } }
        return blink(numbers, 25)
    }

    fun part2(input: List<String>): Long {
        val numbers = input.first().let { it.split(" ").map { it.toLong() } }
        return blink(numbers, 75)
    }

    val testInput = readInput("Day11_test")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 55312L) { "part 1 test failed" }

    val input = readInput("Day11")

    print("Part 1: ")
    part1(input).println()

    print("Part 2: ")
    part2(input).println()
}
