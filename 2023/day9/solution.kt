package day9

import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "

fun readAndParseInput() {
    val file = File(if (actual) inputFile else demoInputFile)

    file.forEachLine { line ->

    }
}

fun main()  {
    actual = false
    val input = readAndParseInput()

}