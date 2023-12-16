package day16

import aocutils.Point
import aocutils.directionToPair
import aocutils.pairToDirection
import aocutils.println
import java.io.File
import kotlin.math.max

private const val INPUT = "Kotlin/src/main/kotlin/day16/input.txt"
private const val DEMO_INPUT = "Kotlin/src/main/kotlin/day16/demo_input.txt"
var actual = false
private const val PART1 = "Part 1: "
private const val PART2 = "Part 2: "
private val N = directionToPair['N']!!
private val S = directionToPair['S']!!
private val E = directionToPair['E']!!
private val W = directionToPair['W']!!
private val beamDeflector = mapOf(
    '\\' to mapOf(
        'N' to listOf(W),
        'S' to listOf(E),
        'E' to listOf(S),
        'W' to listOf(N)
    ),
    '/' to mapOf(
        'N' to listOf(E),
        'S' to listOf(W),
        'E' to listOf(N),
        'W' to listOf(S)
    ),
    '|' to mapOf(
        'N' to listOf(N),
        'S' to listOf(S),
        'E' to listOf(N, S),
        'W' to listOf(N, S)
    ),
    '-' to mapOf(
        'N' to listOf(W, E),
        'S' to listOf(W, E),
        'E' to listOf(E),
        'W' to listOf(W)
    )
)

data class Beam(val start: Point, val direction: Char)

fun readAndParseInput(): List<String> =
    File(if (actual) INPUT else DEMO_INPUT)
        .readLines()

fun solve(contraption: List<String>, start: Point, direction: Char): Int    {
    val beamDirections = mutableListOf(Beam(start = start, direction = direction))
    val visited = mutableSetOf<Beam>()
    while (beamDirections.isNotEmpty()) {
        val (st, dr) = beamDirections.removeFirst()
        var (x, y) = st
        val (dx, dy) = directionToPair[dr]!!
        while (y in contraption.indices && x in contraption[0].indices && contraption[y][x] == '.') {
            visited.add(Beam(start = Point(x = x, y = y), direction = dr))
            x += dx
            y += dy
        }
        if (y !in contraption.indices || x !in contraption[0].indices)  continue
        if (!visited.add(Beam(start = Point(x = x, y = y), direction = dr)))  continue
        val newDirections = beamDeflector[contraption[y][x]]!![dr]!!
        beamDirections.addAll(newDirections.map { Beam(start = Point(x = x + it.first, y = y + it.second), direction = pairToDirection[it]!!) })
    }
    return visited.map { it.start }.toSet().size
}

fun solvePart1(contraption: List<String>): Int =
    solve(contraption, Point(0, 0), 'E')

fun solvePart2(contraption: List<String>): Int  {
    var ans = 0
    for (row in contraption.indices)    {
        ans = max(ans, solve(contraption, Point(0, row), 'E'))
        ans = max(ans, solve(contraption, Point(contraption[0].lastIndex, row), 'W'))
    }
    for (col in contraption[0].indices) {
        ans = max(ans, solve(contraption, Point(col, 0), 'S'))
        ans = max(ans, solve(contraption, Point(col, contraption.lastIndex), 'N'))
    }
    return ans
}

fun main()  {
    actual = true
    val contraption = readAndParseInput()
    solvePart1(contraption = contraption).println(PART1) // 6740
    solvePart2(contraption = contraption).println(PART2) // 7041
}