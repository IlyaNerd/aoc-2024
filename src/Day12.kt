fun main() {
    fun findAreas(map: List<List<Char>>): List<List<Point>> {
        val areas = mutableListOf<List<Point>>()
        val seen = mutableSetOf<Point>()

        fun Point.isOutOfBounds() = x < 0 || y < 0 || x > map.first().lastIndex || y > map.lastIndex

        fun findArea(pot: Char, point: Point): List<Point> {
            if (seen.contains(point)) return emptyList()
            seen.add(point)
            val result = mutableListOf(point)

            fun go(next: (Point) -> Point) {
                val nextPoint = next(point)
                if (!nextPoint.isOutOfBounds()) {
                    val newPot = map[nextPoint]
                    if (newPot == pot) {
                        result.addAll(findArea(pot, nextPoint))
                    }
                }
            }

            go { it.left() }
            go { it.right() }
            go { it.top() }
            go { it.bottom() }

            return result
        }

        map.forEachIndexed { i, row ->
            row.forEachIndexed { j, char ->
                findArea(char, Point(i, j)).takeIf { it.isNotEmpty() }?.let(areas::add)
            }
        }

        return areas
    }

    fun part1(input: List<String>): Int {
        val map = input.twoD()
        val areas = findAreas(map)

        fun List<Point>.perimeter(): Int {
            var count = 0
            this.forEach { point ->
                var budget = 4
                if (this.contains(point.left())) budget--
                if (this.contains(point.right())) budget--
                if (this.contains(point.top())) budget--
                if (this.contains(point.bottom())) budget--
                count += budget
            }
            return count
        }

        return areas.sumOf {
            it.size * it.perimeter()
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day12_test")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 1930) { "part 1 test failed" }

    val input = readInput("Day12")

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 1) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
