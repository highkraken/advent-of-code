package day06

import aocutils.extractAllIntegers
import aocutils.println
import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/day06/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day06/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "

fun readAndParseInput(): Pair<List<Int>, List<Int>> {
    val file = File(if (actual) inputFile else demoInputFile)
    val (time, distance) = file.readLines().map { it.extractAllIntegers().map { it.toInt() } }
    return time to distance
}

fun findNumberOfWays(time: Long, distance: Long): Long {
    var count = 0L
    (0 until  time / 2).forEach { t ->
        if (t * (time - t) > distance)  count += 2
    }
    if ((time / 2) * (time - time / 2) > distance) {
        count += if (time % 2 == 0L) 1 else 2
    }
    return count
}

fun solvePart1(time: List<Int>, distance: List<Int>): Int   {
    var ans = 1L
    for (index in time.indices) {
        val currTime = time[index]
        val currDistance = distance[index]
        ans *= findNumberOfWays(time = currTime.toLong(), distance = currDistance.toLong())
    }
    return ans.toInt()
}

fun solvePart2(time: List<Int>, distance: List<Int>): Long  {
    val theTime = time.joinToString(separator = "")
    val theDistance = distance.joinToString(separator = "")
    return findNumberOfWays(time = theTime.toLong(), distance = theDistance.toLong())
}

fun main()  {
    actual = true
    val (time, distance) = readAndParseInput()
    solvePart1(time = time, distance = distance).println(part1) // 771628
    solvePart2(time = time, distance = distance).println(part2) // 27363861
}