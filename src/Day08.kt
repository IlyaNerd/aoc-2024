fun main() {
    data class Point(val x: Int, val y: Int) : Comparable<Point> {
        override fun compareTo(other: Point): Int {
            return Comparator.comparingInt<Point> { t -> t.x }.thenComparing { t -> t.y }.compare(this, other)
        }
    }

    operator fun List<List<Char>>.get(point: Point) = this[point.x][point.y]
    operator fun Point.minus(other: Point): Point = Point(x - other.x, y - other.y)
    operator fun Point.plus(other: Point): Point = Point(x + other.x, y + other.y)

    fun antennas(input: List<String>): Map<Char, Set<Pair<Point, Point>>> {
        return input.mapIndexed { i, line ->
            line.mapIndexed { j, char ->
                if (char != '.') char to Point(i, j)
                else null
            }.filterNotNull()
        }.flatten().groupBy({ it.first }, { it.second })
            .mapValues { (_, points) ->
                cartesianProduct(listOf(points, points))
                    .map { it.sorted() }
                    .map { it[0] to it[1] }
                    .filter { (f, s) -> f != s }
                    .toSet()
            }
    }

    fun part1(input: List<String>): Int {
        val lastX = input.lastIndex
        val lastY = input.first().lastIndex
        val map = input.twoD()

        fun Point.isValid(char: Char) = x >= 0 && y >= 0 && y <= lastY && x <= lastX && map[this] != char

        val antennas = antennas(input)
        val antinodes = mutableSetOf<Point>()

        antennas.forEach { (char, locations) ->
            locations.forEach { (x, y) ->
                val diff = x - y
                if (diff.x > 0) {
                    (x - diff).let { if (it.isValid(char)) antinodes.add(it) }
                    (y + diff).let { if (it.isValid(char)) antinodes.add(it) }
                } else if (diff.x < 0) {
                    (x + diff).let { if (it.isValid(char)) antinodes.add(it) }
                    (y - diff).let { if (it.isValid(char)) antinodes.add(it) }
                } else error("same x? $x $y")
            }
        }

        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val lastX = input.lastIndex
        val lastY = input.first().lastIndex

        fun Point.isOutOfBounds() = x < 0 || y < 0 || y > lastY || x > lastX

        val antennas = antennas(input)
        val antinodes = mutableSetOf<Point>()

        fun captureAntinodes(start: Point, next: (Point) -> Point) {
            antinodes.add(start)
            var node = next(start)
            while (true) {
                if (node.isOutOfBounds()) break
                antinodes.add(node)
                node = next(node)
            }
        }

        antennas.forEach { (_, locations) ->
            locations.forEach { (x, y) ->
                val diff = x - y
                if (diff.x > 0) {
                    captureAntinodes(x) { it - diff }
                    captureAntinodes(y) { it + diff }
                } else if (diff.x < 0) {
                    captureAntinodes(x) { it + diff }
                    captureAntinodes(y) { it - diff }
                } else error("same x? $x $y")
            }
        }

        return antinodes.size
    }

    val testInput = readInput("Day08_test")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 14) { "part 1 test failed" }

    val input = readInput("Day08")

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 34) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
