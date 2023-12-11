package day10

import aocutils.Pii
import aocutils.Point
import aocutils.println
import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/day10/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day10/demo_input2.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "
var start = Point(x = -1, y = -1)

fun Pii.getNewDirection(shape: Char): Pii?  {
    if (this.canBeEnteredInto(shape))   {
        val (x, y) = this
        return when {
            x == 1 ->   {
                when (shape)    {
                    '7' -> 0 to 1
                    'J' -> 0 to -1
                    '-' -> this
                    else -> null
                }
            }
            x == -1 ->  {
                when (shape)    {
                    'F' -> 0 to 1
                    'L' -> 0 to -1
                    '-' -> this
                    else -> null
                }
            }
            y == 1 ->   {
                when (shape)    {
                    'J' -> -1 to 0
                    'L' -> 1 to 0
                    '|' -> this
                    else -> null
                }
            }
            y == -1 ->  {
                when (shape)    {
                    '7' -> -1 to 0
                    'F' -> 1 to 0
                    '|' -> this
                    else -> null
                }
            }
            else -> null
        }
    }
    return null
}

fun Pii.canBeEnteredInto(shape: Char): Boolean  {
    if (shape == 'S')   return true
    return when (this)  {
        1 to 0 -> shape == '-' || shape == 'J' || shape == '7'
        -1 to 0 -> shape == '-' || shape == 'L' || shape == 'F'
        0 to 1 -> shape == '|' || shape == 'L' || shape == 'J'
        0 to -1 -> shape == '|' || shape == '7' || shape == 'F'
        else -> false
    }
}

val pipes = mutableListOf<Point>()

fun readAndParseInput(): List<String> =
    File(if (actual) inputFile else demoInputFile)
        .readLines()

fun solvePart1(field: List<String>): Long   {
    val list = mutableListOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)
    list.removeAll { direction ->
        start.y + direction.second !in field.indices ||
                start.x + direction.first !in field[0].indices ||
                !direction
                    .canBeEnteredInto(field[start.y + direction.second][start.x + direction.first])
    }
    var (point1, point2) = list.map { Point(x = start.x + it.first, y = start.y + it.second) }
    var (dir1, dir2) = list.map { it.getNewDirection(field[start.y + it.second][start.x + it.first])!! }
    var steps = 1L
    while (point1 != point2)    {
        pipes.add(point1)
        pipes.add(point2)
        val n1 = dir1.getNewDirection(field[point1.y + dir1.second][point1.x + dir1.first])
        if (n1 != null) {
            point1 = Point(x = point1.x + dir1.first, y = point1.y + dir1.second)
            dir1 = n1
        }
        val n2 = dir2.getNewDirection(field[point2.y + dir2.second][point2.x + dir2.first])
        if (n2 != null) {
            point2 = Point(x = point2.x + dir2.first, y = point2.y + dir2.second)
            dir2 = n2
        }
        steps++
    }
    pipes.add(point1)
    return steps
}

fun solvePart2(field: List<String>): Long   {
    val groupedByY = pipes
        .sortedWith(compareBy({ it.y }, { it.x }))
        .groupBy({ it.y }, { it.x })
    var tilesEnclosed = 0L
    for ((row, cols) in groupedByY) {
        cols
            .filter { field[row][it] in "LJ|" }
            .windowed(size = 2, step = 2)   { (from, to) ->
                for (ind in from..to)   {
                    if (ind !in cols)   tilesEnclosed++
                }
            }
    }
    return tilesEnclosed
}

fun main()  {
    actual = true
    val field = readAndParseInput()
    outer@ for (row in field.indices)  {
        for (col in field[row].indices) {
            if (field[row][col] == 'S') {
                start = Point(x = col, y = row)
                pipes.add(start)
                break@outer
            }
        }
    }
    solvePart1(field).println(part1) // 6773
    solvePart2(field).println(part2) // 493
}