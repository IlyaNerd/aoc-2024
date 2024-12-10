fun main() {
    data class Point(val x: Int, val y: Int)

    operator fun List<List<Char>>.get(point: Point) = this[point.x][point.y]

    fun Point.left() = copy(y = y - 1)
    fun Point.right() = copy(y = y + 1)
    fun Point.top() = copy(x = x - 1)
    fun Point.bottom() = copy(x = x + 1)

    fun getPaths(input: List<String>): List<Pair<Point, List<List<Point>>>> {
        val map = input.twoD()
        val startingPoints: List<Point> = map.flatMapIndexed { i, chars ->
            chars.mapIndexed { j, char ->
                if (char == '0') Point(i, j)
                else null
            }.filterNotNull()
        }

        fun Point.isOutOfBounds() = x < 0 || y < 0 || x > map.first().lastIndex || y > map.lastIndex

        fun findPaths(start: Point, list: MutableList<Point>): MutableList<List<Point>> {
            val result = mutableListOf<List<Point>>(list)
            val startChar = map[start].digitToInt()

            fun go(next: (Point) -> Point) {
                val nextPoint = next(start)
                if (!nextPoint.isOutOfBounds()) {
                    val lChar = map[nextPoint].digitToInt()
                    if (lChar - startChar == 1) {
                        result.addAll(findPaths(nextPoint, list.toMutableList().also { it.add(nextPoint) }))
                    }
                }
            }

            go { it.left() }
            go { it.right() }
            go { it.top() }
            go { it.bottom() }

            return result
        }

        return startingPoints.map { start ->
            start to findPaths(start, mutableListOf(start))
                .filter { list -> list.size == 10 }
        }
    }

    fun part1(input: List<String>): Int {
        return getPaths(input).sumOf { (_, paths) ->
            paths.distinctBy { list -> list.last() }.size
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day10_test")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 36) { "part 1 test failed" }

    val input = readInput("Day10")

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 1) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
