import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) =
    Path("src/$name.txt")
        .also { check(it.exists()) { "File $name.txt doesnt exist" } }
        .readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun <T> T.println(): T = also { println(this) }

fun List<String>.twoD(): List<List<Char>> = map { string -> string.toCharArray().toList() }

data class Point(val x: Int, val y: Int)

operator fun List<List<Char>>.get(point: Point) = this[point.x][point.y]

fun Point.left() = copy(y = y - 1)
fun Point.right() = copy(y = y + 1)
fun Point.top() = copy(x = x - 1)
fun Point.bottom() = copy(x = x + 1)

fun pleft(x: Int, y: Int) = Point(x = x, y = y - 1)
fun pright(x: Int, y: Int) = Point(x = x, y = y + 1)
fun ptop(x: Int, y: Int) = Point(x = x - 1, y = y)
fun pbottom(x: Int, y: Int) = Point(x = x + 1, y = y)