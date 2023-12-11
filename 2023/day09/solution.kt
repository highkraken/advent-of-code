package day09

import aocutils.extractAllIntegers
import aocutils.println
import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/day9/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day9/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "

fun List<Long>.isArithmeticProgression(): Boolean  {
    if (this.size < 2)  return true
    val d = this[1] - this[0]
    for (ind in 2..this.lastIndex)  {
        if (this[ind] - this[ind - 1] != d) return false
    }
    return true
}

fun readAndParseInput(): List<List<Long>> =
    File(if (actual) inputFile else demoInputFile)
        .readLines()
        .map { it.extractAllIntegers() }

fun generateProgressionReport(history: List<Long>): List<List<Long>>    {
    val progression = mutableListOf(history.toMutableList())
    while (!progression.last().isArithmeticProgression())   {
        val next = mutableListOf<Long>()
        val curr = progression.last()
        for (ind in 1..curr.lastIndex)    {
            next.add(curr[ind] - curr[ind - 1])
        }
        progression.add(next)
    }
    val a = progression.last()[0]
    val d = progression.last()[1] - progression.last()[0]
    progression.last().add(a + progression[progression.lastIndex].size * d)
    progression.last().add(index = 0, element = a - d)
    for (ind in progression.lastIndex - 1 downTo 0) {
        progression[ind].add(progression[ind].last() + progression[ind + 1].last())
        progression[ind].add(index = 0, element = progression[ind].first() - progression[ind + 1].first())
    }
    return progression.map { it.toList() }
}

fun solvePart1(histories: List<List<Long>>): Long =
    histories.sumOf { history ->
        generateProgressionReport(history).first().last()
    }

fun solvePart2(histories: List<List<Long>>): Long =
    histories.sumOf { history ->
        generateProgressionReport(history).first().first()
    }

fun main()  {
    actual = true
    val histories = readAndParseInput()
    solvePart1(histories = histories).println(part1) // 1969958987
    solvePart2(histories = histories).println(part2) // 1068
}