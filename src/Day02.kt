fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day02_test")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 1) { "part 1 test failed" }

    val part2Test = part1(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 1) { "part 2 test failed" }

    val input = readInput("Day02")
    print("Part 1: ")
    part1(input).println()
    print("Part 2: ")
    part2(input).println()
}
