package day

import java.io.File
import aocutils.*

private val DAY = (object {}).javaClass.packageName
private val INPUT = "$DAY/input.txt"
private val DEMO_INPUT = "$DAY/demo_input.txt"
private var actual = false
private const val PART1 = "Part 1: "
private const val PART2 = "Part 2: "

private fun readAndParseInput() {
    val file = File(if (actual) INPUT else DEMO_INPUT)

    file.forEachLine { line ->

    }
}

fun solve() {

}

private fun main()  {
    actual = false
    val input = readAndParseInput()

}