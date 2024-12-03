fun main() {
    val numberRegex = Regex("mul\\((\\d+),(\\d+)\\)")

    fun part1(input: String): Int {
        val nums = numberRegex.findAll(input).map { matchResult ->
            matchResult.groupValues[1].toInt() to matchResult.groupValues[2].toInt()
        }.toList()
        return nums.sumOf { (l, r) -> l * r }
    }

    fun part2(input: String): Int {
        val regex = Regex("(mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\))")
        var sum = 0
        var allowed = true
        regex.findAll(input).forEach { matchResult ->
            val instruction = matchResult.groupValues[1]
            when {
                instruction.startsWith("mul(") -> if (allowed) {
                    numberRegex.findAll(instruction).forEach { matchResult ->
                        val left = matchResult.groupValues[1].toInt()
                        val right = matchResult.groupValues[2].toInt()
                        sum += left * right
                    }
                }

                instruction.startsWith("do(") -> allowed = true
                instruction.startsWith("don't(") -> allowed = false
            }
            instruction
        }
        return sum
    }

    val testInput = readInput("Day03_test").joinToString("")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 161) { "part 1 test failed" }

    val input = readInput("Day03").joinToString("")

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 48) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
