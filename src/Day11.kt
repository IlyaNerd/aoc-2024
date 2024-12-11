import kotlin.math.pow

fun main() {
    val rules: List<(Long) -> List<Long>?> = listOf(
        { if (it == 0L) listOf(1L) else null },
        {
            val string = it.toString()
            if (string.length % 2 == 0) {
                val div = 10.0.pow(string.length / 2.0).toLong()
                listOf(it / div, it % div)
            }
            else null
        },
        { listOf(it * 2024L) },
    )

    fun blink(input: List<Long>): List<Long> {
        return input.flatMap { lng ->
            rules.firstNotNullOf { rule -> rule(lng) }
        }
    }

    fun part1(input: List<String>): Int {
        val numbers = input.first().let { it.split(" ").map { it.toLong() } }
        return (1..25).fold(numbers) { agg, _ ->
            blink(agg)
        }.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day11_test")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 55312) { "part 1 test failed" }

    val input = readInput("Day11")

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 1) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
