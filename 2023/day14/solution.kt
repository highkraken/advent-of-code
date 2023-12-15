package day14

import aocutils.println
import day13.transpose
import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/day14/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day14/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "
val directionMap = mapOf('N' to (true to true), 'S' to (true to false), 'E' to (false to false), 'W' to (false to true))

fun String.sort(reversed: Boolean = false): String  {
    val ret = if (reversed) this.toList().sortedDescending() else this.toList().sorted()
    return ret.joinToString(separator = "")
}

fun List<String>.tilt(direction: Char): List<String>    {
    val (transpose, reverse) = directionMap[direction]!!
    var positions = this
    if (transpose)  positions = positions.transpose()
    positions = positions.map { row ->
        row.split("#").joinToString(separator = "#") {
            it.sort(reversed = reverse)
        }
    }
    if (transpose)  positions = positions.transpose()
    return positions
}

fun readAndParseInput(): List<String> =
    File(if (actual) inputFile else demoInputFile)
        .readLines()

fun solvePart1(platform: List<String>): Long    {
    val tiltedPlatform = platform.tilt(direction = 'N')
    var load = 0L
    for (row in tiltedPlatform.withIndex())    {
        load += row.value.count { it == 'O' } * (tiltedPlatform.size - row.index)
    }
    return load
}

fun solvePart2(platform: List<String>): Long    {
    var tiltedPlatform = platform
    val set = mutableSetOf(tiltedPlatform)
    val list = mutableListOf(tiltedPlatform)
    val cycle: () -> Unit = {
        tiltedPlatform = tiltedPlatform.tilt(direction = 'N')
        tiltedPlatform = tiltedPlatform.tilt(direction = 'W')
        tiltedPlatform = tiltedPlatform.tilt(direction = 'S')
        tiltedPlatform = tiltedPlatform.tilt(direction = 'E')
    }
    var iteration = 0
    while (true)    {
        iteration++
        cycle()
        if (tiltedPlatform in set)
            break
        set.add(tiltedPlatform)
        list.add(tiltedPlatform)
    }
    val firstCycleStart = list.indexOf(tiltedPlatform)
    tiltedPlatform = list[(1_00_00_00_000 - firstCycleStart) % (iteration - firstCycleStart) + firstCycleStart]
    var load = 0L
    for (row in tiltedPlatform.withIndex())    {
        load += row.value.count { it == 'O' } * (tiltedPlatform.size - row.index)
    }
    return load
}

fun main()  {
    actual = true
    val platform = readAndParseInput()
    solvePart1(platform = platform).println(part1) // 109385
    solvePart2(platform = platform).println(part2) // 93102
}