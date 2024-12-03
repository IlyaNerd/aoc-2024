fun main() {
    fun part1(input: List<String>): Int {
        val nums = input.joinToString("").let {
            val regex = Regex("mul\\((\\d+),(\\d+)\\)")
            regex.findAll(it).map { matchResult ->
                matchResult.groupValues[1].toInt() to matchResult.groupValues[2].toInt()
            }.toList()
        }
        return nums.sumOf { (l, r) -> l * r }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day03_test")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 161) { "part 1 test failed" }

    val input = readInput("Day03")

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 1) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
