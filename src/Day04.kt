fun main() {
    fun part1(input: List<List<Char>>): Int {
        fun checkRight(j: Int, line: List<Char>): Boolean {
            if (j + 3 > line.lastIndex) return false
            return line[j + 1] == 'M' && line[j + 2] == 'A' && line[j + 3] == 'S'
        }

        fun checkLeft(j: Int, line: List<Char>): Boolean {
            if (j - 3 < 0) return false
            return line[j - 1] == 'M' && line[j - 2] == 'A' && line[j - 3] == 'S'
        }

        fun checkTop(i: Int, j: Int): Boolean {
            if (i - 3 < 0) return false
            return input[i - 1][j] == 'M' && input[i - 2][j] == 'A' && input[i - 3][j] == 'S'
        }

        fun checkDown(i: Int, j: Int): Boolean {
            if (i + 3 > input.lastIndex) return false
            return input[i + 1][j] == 'M' && input[i + 2][j] == 'A' && input[i + 3][j] == 'S'
        }

        fun checkDiagonalTopLeft(i: Int, j: Int): Boolean {
            if (i - 3 < 0 || j - 3 < 0) return false
            return input[i - 1][j - 1] == 'M' && input[i - 2][j - 2] == 'A' && input[i - 3][j - 3] == 'S'
        }

        fun checkDiagonalTopRight(i: Int, j: Int): Boolean {
            if (i - 3 < 0 || j + 3 > input.first().lastIndex) return false
            return input[i - 1][j + 1] == 'M' && input[i - 2][j + 2] == 'A' && input[i - 3][j + 3] == 'S'
        }

        fun checkDiagonalDownLeft(i: Int, j: Int): Boolean {
            if (i + 3 > input.lastIndex || j - 3 < 0) return false
            return input[i + 1][j - 1] == 'M' && input[i + 2][j - 2] == 'A' && input[i + 3][j - 3] == 'S'
        }

        fun checkDiagonalDownRight(i: Int, j: Int): Boolean {
            if (i + 3 > input.lastIndex || j + 3 > input.lastIndex) return false
            return input[i + 1][j + 1] == 'M' && input[i + 2][j + 2] == 'A' && input[i + 3][j + 3] == 'S'
        }

        var count = 0

        input.forEachIndexed { i, line ->
            line.forEachIndexed { j, char ->
                if (char == 'X') {
                    if (checkRight(j, line)) count++
                    if (checkLeft(j, line)) count++
                    if (checkTop(i, j)) count++
                    if (checkDown(i, j)) count++
                    if (checkDiagonalTopLeft(i, j)) count++
                    if (checkDiagonalTopRight(i, j)) count++
                    if (checkDiagonalDownLeft(i, j)) count++
                    if (checkDiagonalDownRight(i, j)) count++
                }
            }
        }

        return count
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
