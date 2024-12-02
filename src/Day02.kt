fun main() {
    fun parse(input: List<String>): List<List<Int>> {
        return input.map { string ->
            string.split(" ").map { it.toInt() }
        }
    }

    fun List<Int>.isSafe(): Boolean {
        val isAsc = (this[1] - this[0]) > 0
        this.forEachIndexed { index, number ->
            if (index != 0) {
                val prev = this[index - 1]
                val diff = number - prev
                when {
                    diff == 0 -> return false
                    diff > 3 || diff < -3 -> return false
                    diff > 0 && !isAsc -> return false
                    diff < 0 && isAsc -> return false
                }
            }
        }
        return true
    }

    fun List<Int>.isSafeWithTolerance(): Boolean {
        if (isSafe()) {
            return true
        } else {
            indices.forEach { i ->
                val mutable = this.toMutableList()
                mutable.removeAt(i)
                if (mutable.isSafe()) return true
            }
            return false
        }
    }

    fun part1(input: List<String>): Int {
        val reports = parse(input)
        return reports.count { report -> report.isSafe() }
    }

    fun part2(input: List<String>): Int {
        val reports = parse(input)
        return reports.count { report -> report.isSafeWithTolerance() }
    }

    val testInput = readInput("Day02_test")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 2) { "part 1 test failed" }

    val input = readInput("Day02")

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 4) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
