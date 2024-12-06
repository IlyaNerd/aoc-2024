fun main() {
    data class Point(val x: Int, val y: Int)

    fun parse(input: List<String>): List<List<Char>> {
        return input.map { it.toCharArray().toList() }
    }

    fun part1(input: List<String>): Int {
        val map = parse(input)
        val lastX = map.lastIndex
        val lastY = map.first().lastIndex
        val visited = mutableSetOf<Point>()

        operator fun List<List<Char>>.get(point: Point) = this[point.x][point.y]

        fun Point.isOutOfBounds() = x < -1 || y < -1 || x > lastX || y > lastY

        // null is out of the map
        fun go(from: Point, takeNext: (Point) -> Point): Point? {
            if (from.x == 0 || from.y == 0 || from.x == lastX || from.y == lastY) return null
            var prev = from
            var next = takeNext(from)
            while (true) {
                if (next.isOutOfBounds()) return null
                var char = map[next]
                if (char == '#') return prev
                prev = next
                visited.add(next)
                next = takeNext(next)
            }
        }

        val startLocation = map.mapIndexed { i, chars -> Point(i, chars.indexOf('^')) }.first { it.y != -1 }
        visited.add(startLocation)

        var lastPoint = startLocation
        val directions = listOf(1, 2, 3, 4)
        generateSequence(0) { (it + 1) % directions.size }
            .first { i ->
                val direction = directions[i]
                val next = when (direction) {
                    1 -> go(lastPoint) { it.copy(x = it.x - 1) } // UP
                    2 -> go(lastPoint) { it.copy(y = it.y + 1) } // RIGHT
                    3 -> go(lastPoint) { it.copy(x = it.x + 1) } // DOWN
                    4 -> go(lastPoint) { it.copy(y = it.y - 1) } // LEFT
                    else -> error("unreachable")
                }
                if (next == null) true
                else {
                    lastPoint = next
                    false
                }
            }

        return visited.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day06_test")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 41) { "part 1 test failed" }

    val input = readInput("Day06")

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 1) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
