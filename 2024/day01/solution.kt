package day01

import java.io.File
import aocutils.*
import kotlin.math.abs

private const val INPUT = "day01/input.txt"
private const val DEMO_INPUT = "demo_input.txt"
private var actual = false
private const val PART1 = "Part 1: "
private const val PART2 = "Part 2: "

private fun readAndParseInput(): List<Pii> {
    val file = File(if (actual) INPUT else DEMO_INPUT)
    val (list1, list2) = mutableListOf<Int>() to mutableListOf<Int>()
    file.forEachLine { line ->
        val (num1, num2) = line.split("   ").map(String::toInt)
        list1.add(num1)
        list2.add(num2)
    }
    list1.sort()
    list2.sort()
    return list1.zip(list2)
}

fun solveP1(input: List<Pii>): Int =
    input.sumOf { abs(it.first - it.second) }

fun solveP2(input: List<Pii>): Int {
    val (list1, list2) = input.unzip()
    return list1.sumOf {
        it * list2.count { i -> i == it }
    }
}

private fun main() {
    actual = true
    val input = readAndParseInput()
    solveP1(input).println(PART1) // 1970720
    solveP2(input).println(PART2) // 17191599
}