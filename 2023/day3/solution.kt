package day3

import java.io.File
import aocutils.*

const val inputFile = "Kotlin/src/main/kotlin/day3/input.txt"
const val demoInputFile = "Kotlin/src/main/kotlin/day3/demo_input.txt"
var actual = false
const val part1 = "Part 1: "
const val part2 = "Part 2: "
val directions = listOf(
    Point(x = 1, y = 0), // right
    Point(x = -1, y = 0),// left
    Point(x = 0, y = 1), // down
    Point(x = 1, y = 1), // down-right
    Point(x = -1, y = 1),// down-left
    Point(x = 0, y = -1),// up
    Point(x = 1, y = -1),// up-right
    Point(x = -1, y = -1)// up-left
)

fun readAndParseInput(): List<String>  =
    File(if (actual) inputFile else demoInputFile).readLines()

fun getSymbolIndices(input: List<String>, onlyAsterisks: Boolean=false): List<aocutils.Point>    {
    val indices = mutableListOf<aocutils.Point>()
    for (i in input.indices)    {
        for (j in input[0].indices) {
            if (!onlyAsterisks && input[i][j] != '.' && !input[i][j].isDigit())   {
                indices.add(Point(x = j, y = i))
            }
            if (onlyAsterisks && input[i][j] == '*')    {
                indices.add(Point(x = j, y = i))
            }
        }
    }
    return indices.toList()
}

fun extractEnginePart(schematicLine: String, fromIndex: Int): Int   {
    var number = schematicLine[fromIndex].toString()
    var (l, r) = fromIndex - 1 to fromIndex + 1
    while (l in schematicLine.indices && schematicLine[l].isDigit())    {
        number = schematicLine[l] + number
        l--
    }
    while (r in schematicLine.indices && schematicLine[r].isDigit())    {
        number += schematicLine[r]
        r++
    }
    return number.toInt()
}

fun solvePart1(engineSchematic: List<String>): Int  {
    val engineParts = mutableListOf<Int>()
    val symbolIndices = getSymbolIndices(engineSchematic)
    symbolIndices.forEach { (x, y) ->
        directions.forEach { (dx, dy) ->
            val (newX, newY) = x + dx to y + dy
            if (engineSchematic[newY][newX] != '.') {
                val enginePart = extractEnginePart(schematicLine = engineSchematic[newY], fromIndex = newX)
                if (engineParts.isEmpty() || enginePart != engineParts.last())
                    engineParts.add(enginePart)
            }
        }
    }
    return engineParts.sum()
}

fun solvePart2(engineSchematic: List<String>): Int   {
    var ans = 0
    val gearSymbols = getSymbolIndices(input = engineSchematic, onlyAsterisks = true)
    gearSymbols.forEach { (x, y) ->
        val engineParts = mutableListOf<Int>()
        directions.forEach { (dx, dy) ->
            val (newX, newY) = x + dx to y + dy
            if (engineSchematic[newY][newX] != '.') {
                val enginePart = extractEnginePart(schematicLine = engineSchematic[newY], fromIndex = newX)
                if (engineParts.isEmpty() || enginePart != engineParts.last())
                    engineParts.add(enginePart)
            }
        }
        if (engineParts.size == 2)  ans += engineParts[0] * engineParts[1]
    }
    return ans
}

fun main()  {
    actual = true
    val input = readAndParseInput()
    solvePart1(input).println(part1) // 551094
    solvePart2(input).println(part2) // 80179647
}