package day

import java.io.File

private const val INPUT = "Kotlin/src/main/kotlin/input.txt"
private const val DEMO_INPUT = "Kotlin/src/main/kotlin/demo_input.txt"
private var actual = false
private const val PART1 = "Part 1: "
private const val PART2 = "Part 2: "

private fun readAndParseInput() {
    val file = File(if (actual) INPUT else DEMO_INPUT)

    file.forEachLine { line ->

    }
}

private fun main()  {
    actual = false
    val input = readAndParseInput()

}