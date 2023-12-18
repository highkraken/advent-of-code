package day18

import aocutils.println
import java.io.File
import kotlin.math.abs
import kotlin.math.pow

private const val INPUT = "Kotlin/src/main/kotlin/day18/input.txt"
private const val DEMO_INPUT = "Kotlin/src/main/kotlin/day18/demo_input.txt"
private var actual = false
private const val PART1 = "Part 1: "
private const val PART2 = "Part 2: "

private data class Move(val direction: String, val amount: Int, val color: String = "")

private fun readAndParseInput(): List<Move> {
    val file = File(if (actual) INPUT else DEMO_INPUT)
    val regex = Regex("""(\w) (\d+) \(#(\w+)\)""")
    val digPlan = mutableListOf<Move>()
    file.forEachLine { line ->
        val (_, direction, amount, color) = regex.find(line)!!.groupValues
        digPlan.add(Move(direction = direction, amount = amount.toInt(), color = color))
    }
    return digPlan.toList()
}

private fun String.toDecimal(): Int    {
    var ret = 0
    val size = this.length - 1
    for (ind in this.indices)   {
        val curr = if (this[ind].isDigit()) { this[ind] - '0' } else { this[ind] - 'a' + 10 }
        ret += curr * 16.0.pow(size - ind).toInt()
    }
    return ret
}

private fun calculateArea(points: MutableList<Pair<Long, Long>>, perimeter: Long): Long   {
    var shoelaceFm = 0L
    points.windowed(3)  { (first, second, third) ->
        shoelaceFm += (second.second * (first.first - third.first))
    }
    shoelaceFm = abs(shoelaceFm) / 2
    val pitsTh = shoelaceFm - (perimeter / 2) + 1
    return pitsTh + perimeter
}

private fun solvePart1(digPlan: List<Move>): Long   { // HyperNeutrino for the rescue https://youtu.be/bGWK76_e-LM?si=N3B8VHwxEeHvI6uK
    val points = mutableListOf(0L to 0L)
    var outerPoints = 0L
    for (move in digPlan)   {
        outerPoints += move.amount
        val last = points.last()
        when (move.direction)   {
            "U" -> points.add(last.copy(second = last.second - move.amount))
            "D" -> points.add(last.copy(second = last.second + move.amount))
            "L" -> points.add(last.copy(first = last.first - move.amount))
            "R" -> points.add(last.copy(first = last.first + move.amount))
        }
    }
    return calculateArea(points = points, perimeter = outerPoints)
}

private fun solvePart2(digPlan: List<Move>): Long   {
    val newDigPlan = mutableListOf<Move>()
    val map = mapOf('0' to "R", '1' to "D", '2' to "L", '3' to "U")
    for ((_, _, color) in digPlan)   {
        val amount = color.substring(0, color.lastIndex).toDecimal()
        val direction = map[color.last()]!!
        println("$color -> $direction $amount")
        newDigPlan.add(Move(direction = direction, amount = amount))
    }
    return solvePart1(digPlan = newDigPlan)
}

private fun main()  {
    actual = true
    val digPlan = readAndParseInput()
    solvePart1(digPlan = digPlan).println(PART1) // 40714
    solvePart2(digPlan = digPlan).println(PART2) // 129849166997110
}