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
