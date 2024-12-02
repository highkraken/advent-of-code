package day02

import java.io.File
import aocutils.*
import kotlin.math.abs

private const val INPUT = "day02/input.txt"
private const val DEMO_INPUT = "day02/demo_input.txt"
private var actual = false
private const val PART1 = "Part 1: "
private const val PART2 = "Part 2: "

private fun readAndParseInput(): List<List<Int>> =
    File(if (actual) INPUT else DEMO_INPUT)
        .readLines()
        .map {
            it.split(" ").map(String::toInt)
        }

fun isReportSafe(input: List<Int>): Boolean {
    var increasing = input[0] < input[1]
    for (i in 1 until input.size) {
        if (increasing && input[i] < input[i - 1] ||
            !increasing && input[i] > input[i - 1] ||
            abs(input[i] - input[i - 1]) !in 1..3
        ) {
            return false
        }
    }
    return true
}

fun solveP1(input: List<List<Int>>): Int =
    input.count(::isReportSafe)

fun solveP2(input: List<List<Int>>): Int =
    input.count { report ->
        report.indices.any { i ->
            val removedElementList = report.toMutableList().apply { removeAt(i) }
            isReportSafe(removedElementList)
        }
    }


private fun main() {
    actual = true
    val input = readAndParseInput()
    solveP1(input).println(PART1) // 390
    solveP2(input).println(PART2) // 439
}