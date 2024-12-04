fun main() {
    fun part1(input: List<List<Char>>): Int {
        return input.size
    }

    fun part2(input: List<List<Char>>): Int {
        return input.size
    }

    val testInput = readInput("Day04_test").map { string -> string.toCharArray().toList() }
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 18) { "part 1 test failed" }

    val input = readInput("Day04").map { string -> string.toCharArray().toList() }

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 1) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
