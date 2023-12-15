package day13

import aocutils.println
import java.io.File
import kotlin.math.min

const val inputFile = "Kotlin/src/main/kotlin/day13/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day13/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "

fun readAndParseInput(): List<String> {
    val file = File(if (actual) inputFile else demoInputFile)
    return file.readLines().joinToString(separator = "\n").split("\n\n")
}

fun findReflection(pattern: List<String>): Int  {
    for (row in 1..pattern.lastIndex)   {
        var above = pattern.subList(0, row).reversed()
        var below = pattern.subList(row, pattern.size)

        val n = min(above.size, below.size)
        above = above.take(n)
        below = below.take(n)

        if (above == below) return row
    }
    return 0
}

fun findNoSmudgeReflection(pattern: List<String>): Int   {
    for (row in 1..pattern.lastIndex)   {
        val above = pattern.subList(0, row).reversed()
        val below = pattern.subList(row, pattern.size)

        var cnt = 0
        for ((ab, bl) in above.zip(below))  {
            cnt += ab.difference(bl)
        }
        if (cnt == 1)   return row
    }
    return 0
}

fun String.difference(that: String): Int    {
    var count = 0
    for (ind in this.indices)   {
        if (this[ind] != that[ind]) count++
    }
    return count
}

fun List<String>.transpose(): List<String>  {
    val list = MutableList(this[0].length) { MutableList(this.size) { '.' } }
    for (row in this[0].indices)    {
        for (col in this.indices)   {
            list[row][col] = this[col][row]
        }
    }
    return list.map { it.joinToString(separator = "") }
}

fun solvePart1(input: List<String>): Long   {
    var ans = 0L
    for (line in input) {
        val pattern = line.split("\n")
        ans += findReflection(pattern) * 100L
        ans += findReflection(pattern.transpose())
    }
    return ans
}

fun solvePart2(input: List<String>): Long   {
    var ans = 0L
    for (line in input) {
        val pattern = line.split("\n")
        ans += findNoSmudgeReflection(pattern) * 100L
        ans += findNoSmudgeReflection(pattern.transpose())
    }
    return ans
}

fun main()  {
    actual = true
    val input = readAndParseInput()
    solvePart1(input).println(part1)
    solvePart2(input).println(part2)
}