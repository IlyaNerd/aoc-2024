fun main() {
    fun part1(input: String): Int {
        return 1
    }

    fun part2(input: String): Int {
        return 1
    }

    val testInput = readInput("Day00_test").first()
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 1) { "part 1 test failed" }

    val input = readInput("Day00").first()

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 1) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
