package day11

import aocutils.Point
import aocutils.println
import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/day11/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day11/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "
val rowsToBeExpanded = mutableListOf<Int>()
val columnsToBeExpanded = mutableListOf<Int>()
val galaxies = mutableListOf<Point>()

fun readAndParseInput(): List<String> =
    File(if (actual) inputFile else demoInputFile)
        .readLines()

fun expandUniverse(image: List<String>) {
    var ind = 0
    outer@ while (ind in 0..image[0].lastIndex) {
        for (row in image)  {
            if (row[ind] != '.') {
                ind++
                continue@outer
            }
        }
        columnsToBeExpanded.add(ind)
        ind++
    }
    ind = 0
    while (ind in image.indices)    {
        if (image[ind].all { it == '.' })   {
            rowsToBeExpanded.add(ind)
            ind++
            continue
        }
        ind++
    }
}

fun extractGalaxyPositions(universe: List<String>)  {
    for (row in universe.indices)   {
        for (col in universe[0].indices)    {
            if (universe[row][col] == '#')  {
                galaxies.add(Point(x = col, y = row))
            }
        }
    }
}

fun distanceAfterExpansion(point1: Point, point2: Point, amount: Int): Long {
    var expansions = 0L
    val manhattan = point1.manhattanDistanceTo(point2).toLong()
    (point1.y..point2.y).forEach { row ->
        if (row in rowsToBeExpanded)    expansions++
    }
    (point2.y..point1.y).forEach { row ->
        if (row in rowsToBeExpanded)    expansions++
    }
    (point1.x..point2.x).forEach { column ->
        if (column in columnsToBeExpanded)  expansions++
    }
    (point2.x..point1.x).forEach { column ->
        if (column in columnsToBeExpanded)  expansions++
    }
    return manhattan + expansions * (amount - 1)
}

fun solve(amount: Int): Long   {
    var ans = 0L
    for (ind in galaxies.indices)   {
        for (it in ind + 1..galaxies.lastIndex) {
            ans += distanceAfterExpansion(point1 = galaxies[ind], point2 = galaxies[it], amount = amount)
        }
    }
    return ans
}

fun main()  {
    actual = true
    val image = readAndParseInput()
    expandUniverse(image = image)
    extractGalaxyPositions(universe = image)
    solve(amount = 2).println(part1) // 10422930
    solve(amount = 10_00_000).println(part2) // 699909023130
}