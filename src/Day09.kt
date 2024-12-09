
fun main() {

    fun parse(input: String): MutableList<Pair<Int, MutableList<Int>?>> {
        var n = 0
        // todo mb result num instead of list
        val list = mutableListOf<Pair<Int, MutableList<Int>?>>()
        input.mapIndexed { i, char ->
            val num = char.digitToInt()
            if (i == 0 || i % 2 == 0) {
                val data = (1..num).map { n }.toMutableList()
                list.add(num to data)
                n++
            } else {
                list.add(num to null)
            }
        }
        return list
    }

    fun part1(input: String): Long {
        val list = parse(input)

        var j = list.lastIndex
        for (i in list.indices) {
            if (i > j) break
            val (size, data) = list[i]
            if (size == 0) continue
            if (data == null) {
                val newData = mutableListOf<Int>()
                var remainingSize = size
                while (true) {
                    val (_, endData) = list[j]
                    if (endData == null || endData.isEmpty()) {
                        j--
                        continue
                    }
                    val iterator = endData.iterator()
                    while (iterator.hasNext()) {
                        val next = iterator.next()
                        iterator.remove()
                        newData.add(next)
                        remainingSize--
                        if (remainingSize == 0) break
                    }
                    if (remainingSize == 0) break
                    if (j == 0 || i == j) break
                }

                list[i] = size to newData
            }
        }

        return list.asSequence().flatMap {
            it.second ?: emptyList()
        }.mapIndexed { i, n ->
            i * n.toLong()
        }.sum()
    }

    fun part2(input: String): Int {
        return 1
    }

    val testInput = readInput("Day09_test").first()
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 1928L) { "part 1 test failed" }

    val input = readInput("Day09").first()

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 1) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
