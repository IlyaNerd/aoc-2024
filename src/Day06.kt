fun main() {
    data class Point(val x: Int, val y: Int)

    fun parse(input: List<String>): MutableList<MutableList<Char>> {
        return input.map { it.toCharArray().toMutableList() }.toMutableList()
    }

    operator fun List<List<Char>>.get(point: Point) = this[point.x][point.y]
    operator fun MutableList<MutableList<Char>>.set(point: Point, char: Char) {
        this[point.x][point.y] = char
    }

    fun part1(input: List<String>): Int {
        val map = parse(input)
        val lastX = map.lastIndex
        val lastY = map.first().lastIndex
        val visited = mutableSetOf<Point>()

        fun Point.isOutOfBounds() = x < 0 || y < 0 || x > lastX || y > lastY

        // null is out of the map
        fun go(from: Point, takeNext: (Point) -> Point): Point? {
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
        val map: MutableList<MutableList<Char>> = parse(input)
        val lastX = map.lastIndex
        val lastY = map.first().lastIndex
        val visitedLines = mutableSetOf<List<Point>>()

        fun Point.isOutOfBounds() = x < 0 || y < 0 || x > lastX || y > lastY

        // null returned is out of the map
        fun go(
            from: Point,
            onVisited: (List<Point>) -> Unit,
            takeNext: (Point) -> Point
        ): Point? {
            var prev = from
            val line = mutableListOf<Point>()
            var next = takeNext(from)
            var exited = false
            while (true) {
                if (next.isOutOfBounds()) {
                    exited = true
                    break
                }
                var char = map[next]
                if (char == '#') break
                prev = next
                line.add(next)
                next = takeNext(next)
            }
            onVisited(line)
            return if (exited) null else prev
        }

        val directions = listOf(1, 2, 3, 4)
        fun traverse(i: Int, lastPoint: Point, onVisited: (List<Point>) -> Unit = { visitedLines.add(it) }): Point? {
            val direction = directions[i]
            return when (direction) {
                1 -> go(lastPoint, onVisited) { it.copy(x = it.x - 1) } // UP
                2 -> go(lastPoint, onVisited) { it.copy(y = it.y + 1) } // RIGHT
                3 -> go(lastPoint, onVisited) { it.copy(x = it.x + 1) } // DOWN
                4 -> go(lastPoint, onVisited) { it.copy(y = it.y - 1) } // LEFT
                else -> error("unreachable")
            }
        }

        val startLocation = map.mapIndexed { i, chars -> Point(i, chars.indexOf('^')) }.first { it.y != -1 }

        run {
            // go through once to record the paths taken
            var lastPoint = startLocation
            generateSequence(0) { (it + 1) % directions.size }
                .first { i ->
                    val next = traverse(i, lastPoint)
                    if (next == null) true
                    else {
                        lastPoint = next
                        false
                    }
                }
        }

        fun isInALoop(): Boolean {
            var inALoop = false
            val visited = mutableSetOf<List<Point>>()
            val onVisited: (List<Point>) -> Unit = { line ->
                inALoop = (if (line.isEmpty()) false // we didnt move
                else !visited.add(line))
            }

            var lastPoint = startLocation
            generateSequence(0) { (it + 1) % directions.size }
                .first { i ->
                    val next = traverse(i, lastPoint, onVisited = onVisited)
                    if (inALoop) {
                        return true
                    }
                    if (next == null) true
                    else {
                        lastPoint = next
                        false
                    }
                }
            return false
        }

        return visitedLines
            .asSequence()
            .flatten()
            .distinct()
            .filter { it != startLocation }
            .count { visited ->
                map[visited] = '#'

                val inALoop = isInALoop()

                map[visited] = '.'
                inALoop
            }
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
    check(part2Test == 6) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
