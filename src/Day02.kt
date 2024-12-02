fun main() {
    fun parse(input: List<String>): List<List<Int>> {
        return input.map { string ->
            string.split(" ").map { it.toInt() }
        }
    }

    fun List<Int>.unsafeNums(takeOne: Boolean = false): List<Int> {
        val isAsc = (this[1] - this[0]) > 0
        val unsafe = mutableListOf<Int>()
        this.forEachIndexed { index, number ->
            if (index != lastIndex) {
                val next = this[index + 1]
                val diff = next - number
                when {
                    diff == 0 -> unsafe.add(index)
                    diff > 3 || diff < -3 -> unsafe.add(index)
                    diff > 0 && !isAsc -> unsafe.add(index)
                    diff < 0 && isAsc -> unsafe.add(index)
                }
            }
            if (takeOne && unsafe.isNotEmpty()) {
                return unsafe
            }
        }
        return unsafe
    }

    fun List<Int>.isSafe(): Boolean {
        return unsafeNums(takeOne = true).isEmpty()
    }

    fun List<Int>.isSafeWithTolerance(): Boolean {
        val unsafe = unsafeNums(takeOne = true)
        if (unsafe.isEmpty()) return true

        // try removing unsafe nums and first and last
        (unsafe + 0 + this.lastIndex).forEach { i ->
            val mutable = this.toMutableList()
            mutable.removeAt(i)
            if (mutable.isSafe()) return true
        }

        // try removing unsafe + 1 nums as we compare 'current' and 'next'; 'next' can be faulty
        unsafe.forEach { i ->
            val mutable = this.toMutableList()
            mutable.removeAt(i + 1)
            if (mutable.isSafe()) return true
        }
        return false
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
