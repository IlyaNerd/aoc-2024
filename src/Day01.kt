import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        input.forEach {
            val (left, right) = it.split("   ")
            list1.add(left.toInt())
            list2.add(right.toInt())
        }

        val sorted1 = list1.sorted()
        val sorted2 = list2.sorted()

        var sum = 0
        sorted1.forEachIndexed { index, num1 ->
            val num2 = sorted2[index]
            sum += (num1 - num2).absoluteValue
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
