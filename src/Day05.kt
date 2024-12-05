fun main() {
    fun parse(input: List<String>): Pair<Set<Pair<Int, Int>>, List<List<Int>>> {
        val splitIndex = input.indexOfFirst { line -> line.contains(",") }
        val rules =
            input.take(splitIndex - 1).map { line -> line.split("|").let { it[0].toInt() to it[1].toInt() } }.toSet()
        val numPages = input.drop(splitIndex).map { line -> line.split(",").map(String::toInt) }
        return Pair(rules, numPages)
    }

    fun part1(input: List<String>): Int {
        val (rulesSet, numPages) = parse(input)
        return numPages.filter { page ->
            val correctLine = page.zipWithNext().all { pair ->
                rulesSet.contains(pair)
            }
            correctLine
        }.sumOf { page ->
            val middleNumber = page[page.size / 2]
            middleNumber
        }
    }

    fun part2(input: List<String>): Int {
        val (rulesSet, numPages) = parse(input)

        return numPages.map { it.toMutableList() }.sumOf { page ->
            fun fixPage(): Boolean {
                var incorrect = false
                for (i in 0..(page.lastIndex - 1)) {
                    val current = page[i]
                    val next = page[i + 1]
                    if (!rulesSet.contains(current to next)) {
                        incorrect = true
                        page[i] = next
                        page[i + 1] = current
                    }
                }
                return incorrect
            }

            var countPage = false

            while (true) {
                // would be nice not to go through the page several times 🤔
                val correct = !fixPage()
                if (correct) break
                else {
                    countPage = true
                }
            }

            if (countPage) {
                val middleNumber = page[page.size / 2]
                middleNumber
            } else {
                0
            }
        }
    }

    val testInput = readInput("Day05_test")
    val part1Test = part1(testInput)
    println("Part 1 test: $part1Test")
    check(part1Test == 143) { "part 1 test failed" }

    val input = readInput("Day05")

    print("Part 1: ")
    part1(input).println()

    val part2Test = part2(testInput)
    println("Part 2 test: $part2Test")
    check(part2Test == 123) { "part 2 test failed" }

    print("Part 2: ")
    part2(input).println()
}
