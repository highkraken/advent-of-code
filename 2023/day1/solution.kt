package day1

import java.io.File
import aocutils.*

const val inputFile = "Kotlin/src/main/kotlin/day1/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day1/demo_input2.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "


fun readFileInput1(): List<Int> {
    val file = File(if (actual) inputFile else demoInputFile)
    val input = mutableListOf<Int>()
    file.forEachLine { line ->
        val integers = Regex("\\d").findAll(line).map { it.value.toInt() }.toList()
        input.add(integers.first() * 10 + integers.last())
    }
    return input.toList()
}


fun readFileInputP2(): List<Int> {
    val file = File(if (actual) inputFile else demoInputFile)
    val input = mutableListOf<Int>()
    val startChar = mapOf(
        'z' to listOf("zero"),
        'o' to listOf("one"),
        't' to listOf("two", "three"),
        'f' to listOf("four", "five"),
        's' to listOf("six", "seven"),
        'e' to listOf("eight"),
        'n' to listOf("nine")
    )
    val map = mapOf("zero" to 0, "one" to 1, "two" to 2, "three" to 3, "four" to 4,
        "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)
    file.forEachLine { line ->
        var i = 0
        val integers = mutableListOf<Int>()
        while (i in line.indices)   {
            if (line[i] in startChar.keys)    {
                startChar[line[i]]!!.forEach { match ->
                    if (i + match.length - 1 in line.indices && line.substring(i, i + match.length) == match)   {
                        integers.add(map[match]!!)
                        i += match.length - 2
                        /*
                        z e r o n e
                        ^   ^
                        i   i+=match.length-2
                         */
                    }
                }
            }
            if (line[i].isDigit())  integers.add("${line[i]}".toInt())
            i++
            /*
            z e r o n e
                  ^
                  i
            to get one as a digit in integers list
             */
        }
        input.add(integers.first() * 10 + integers.last())
    }
    return input.toList()
}

fun solve(input: List<Int>): Int =
    input.sum()


fun main()  {
    actual = true
    val inputP1 = readFileInput1()
    solve(inputP1).println(part1) // 54081
    val inputP2 = readFileInputP2()
    solve(inputP2).println(part2) // 54649
}