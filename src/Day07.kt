import Day07.Operator

fun main() {
    fun parse(input: List<String>): List<Pair<Long, List<Long>>> {
        return input.map { line ->
            val total = line.substringBefore(":").toLong()
            val numbers =
                line.substringAfter(":").split(" ").filter { string -> string.isNotEmpty() }.map { it.toLong() }
            total to numbers
        }
    }

    fun part1(input: List<String>): Long {
        val lines = parse(input)
        return lines.sumOf { (total, numbers) ->
            val combinations = cartesianProduct((1..numbers.lastIndex).map { i -> Operator.entries }).toSet()

            fun isSolvable(combination: List<Operator>): Boolean {
                var t = numbers.first()
                numbers.drop(1).forEachIndexed { i, cur ->
                    val operator = combination[i]
                    val r = when (operator) {
                        Operator.plus -> t + cur
                        Operator.mult -> t * cur
                    }
                    t = r
                    if (t > total) return false
                }
                return t == total
            }

            if (combinations.any { isSolvable(it) }) total
            else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day07_test")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 3749L) { "part 1 test failed" }

    val input = readInput("Day07")

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 1) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}

private object Day07 {
    enum class Operator { plus, mult }
}

// thank you StackOverflow :) https://stackoverflow.com/a/62270662/6498008
fun <T> cartesianProduct(iterables: List<List<T>>): Sequence<List<T>> = sequence {
    val numberOfIterables = iterables.size
    val lstLengths = ArrayList<Int>()
    val lstRemaining = ArrayList(listOf(1))

    iterables.reversed().forEach {
        lstLengths.add(0, it.size)
        lstRemaining.add(0, it.size * lstRemaining[0])
    }

    val nProducts = lstRemaining.removeAt(0)

    (0 until nProducts).forEach { product ->
        val result = ArrayList<T>()
        (0 until numberOfIterables).forEach { iterableIndex ->
            val elementIndex = product / lstRemaining[iterableIndex] % lstLengths[iterableIndex]
            result.add(iterables[iterableIndex][elementIndex])
        }
        yield(result.toList())
    }
}