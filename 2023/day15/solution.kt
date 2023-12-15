package day15

import aocutils.println
import java.io.File

const val inputFile = "Kotlin/src/main/kotlin/day15/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day15/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "

fun readAndParseInput(): List<String> =
    File(if (actual) inputFile else demoInputFile)
        .readText()
        .split(",")

data class Lens(val label: String, val focal: Int)

fun hash(string:String): Int    {
    var value = 0
    for (char in string)    {
        value += char.code
        value *= 17
        value %= 256
    }
    return value
}

fun solvePart1(input: List<String>): Int =
    input.sumOf { hash(it) }

fun solvePart2(input: List<String>): Int    {
    val boxes = MutableList(size = 256) { mutableListOf<Lens>() }
    input.forEach { line ->
        val (label, focal) = line.split(Regex("""[-=]"""))
        val boxIndex = hash(label)
        if (focal.isEmpty())    {
            boxes[boxIndex].removeIf { label == it.label }
        }   else    {
            val lens = Lens(label = label, focal = focal.toInt())
            val lensIndex = boxes[boxIndex].indexOfFirst { it.label == label }
            if (lensIndex != -1)    {
                boxes[boxIndex][lensIndex] = lens
            }   else    {
                boxes[boxIndex].add(lens)
            }
        }
    }
    return boxes.flatMapIndexed { boxIndex, lenses ->
        lenses.mapIndexed { lensIndex, lens ->
            (boxIndex + 1) * (lensIndex + 1) * lens.focal
        }
    }.sum()
}

fun main()  {
    actual = true
    val input = readAndParseInput()
    solvePart1(input = input).println(part1) // 505459
    solvePart2(input = input).println(part2) // 228508
}